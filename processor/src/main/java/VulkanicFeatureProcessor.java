import com.google.auto.service.AutoService;
import com.palantir.javapoet.*;
import net.flamgop.vulkanic.annotations.VulkanExtension;
import net.flamgop.vulkanic.annotations.VulkanFeature;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"net.flamgop.vulkanic.annotations.VulkanFeature", "net.flamgop.vulkanic.annotations.VulkanExtension"})
@SupportedSourceVersion(SourceVersion.RELEASE_25)
public class VulkanicFeatureProcessor extends AbstractProcessor {

    private record FeatureInfo(String name, TypeName structType, String getter) {
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty() || roundEnv.processingOver()) {
            return false;
        }

        ClassName targetClass = ClassName.get("net.flamgop.vulkanic.core.feature", "VulkanicDeviceFeatures");

        TypeSpec.Builder baseClass = TypeSpec.classBuilder("VulkanicFeaturesGenerated")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

        baseClass.addMethod(MethodSpec.methodBuilder("getChainedPNext")
                .addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                .addTypeVariable(com.palantir.javapoet.TypeVariableName.get("T", ClassName.get("org.lwjgl.system", "NativeResource")))
                .returns(com.palantir.javapoet.TypeVariableName.get("T"))
                .addParameter(ParameterizedTypeName.get(ClassName.get(Class.class), com.palantir.javapoet.TypeVariableName.get("T")), "type")
                .build());

        ParameterizedTypeName setType = ParameterizedTypeName.get(Set.class, String.class);
        baseClass.addField(FieldSpec.builder(setType, "extensions")
                .addModifiers(Modifier.PROTECTED, Modifier.FINAL)
                .initializer("new $T<>()", HashSet.class)
                .build());

        List<FeatureInfo> features = new ArrayList<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(VulkanFeature.class)) {
            FeatureInfo info = processVulkanFeature(element, baseClass, targetClass);
            features.add(info);
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(VulkanExtension.class)) {
            processVulkanExtension(element, baseClass, targetClass);
        }

        baseClass.addMethod(buildIsSupportedBy(features));

        try {
            JavaFile file = JavaFile.builder("net.flamgop.vulkanic.core.feature", baseClass.build())
                    .build();
            file.writeTo(processingEnv.getFiler());

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated: " + file.toJavaFileObject().getName());
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to write: " + e.getMessage());
        }

        return true;
    }

    private FeatureInfo processVulkanFeature(Element element, TypeSpec.Builder baseClass, ClassName targetClass) {
        String name = element.getSimpleName().toString();
        baseClass.addField(FieldSpec.builder(boolean.class, name)
                .addModifiers(Modifier.PROTECTED)
                .initializer("false")
                .build());

        VulkanFeature anno = element.getAnnotation(VulkanFeature.class);
        TypeName structType = getStructTypeName(element);
        String getter = anno.setter().isEmpty() ? name : anno.setter();

        MethodSpec.Builder enabler = MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(targetClass)
                .addStatement("this.$L = true", name);

        if (!anno.extension().isEmpty()) {
            enabler.addStatement("this.extensions.add($L)", "\"" + anno.extension() + "\"");
        }

        if (structType.toString().equals("org.lwjgl.vulkan.VkPhysicalDeviceFeatures2")) {
            enabler.addStatement("$L features = getChainedPNext($L.class)", structType, structType);
            if (!anno.setter().equals("$$SPECIAL_IGNORE$$")) enabler.addStatement("features.features().$L(true)", getter);
            enabler.addStatement("return ($T)this", targetClass);
        } else {
            enabler.addStatement("$L features = getChainedPNext($L.class)", structType, structType);
            if (!anno.setter().equals("$$SPECIAL_IGNORE$$")) enabler.addStatement("features.$L(true)", getter);
            enabler.addStatement("return ($T)this", targetClass);
        }

        baseClass.addMethod(enabler.build());

        MethodSpec getterMethod = MethodSpec.methodBuilder("supports" + capitalize(name))
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addStatement("return this.$L", name)
                .build();

        baseClass.addMethod(getterMethod);

        return new FeatureInfo(name, structType, getter);
    }

    private MethodSpec buildIsSupportedBy(List<FeatureInfo> features) {
        ClassName memoryStack = ClassName.get("org.lwjgl.system", "MemoryStack");
        ClassName vulkanicPhysicalDevice = ClassName.get("net.flamgop.vulkanic.core", "VulkanicPhysicalDevice");
        ClassName vk11 = ClassName.get("org.lwjgl.vulkan", "VK11");
        ClassName features2 = ClassName.get("org.lwjgl.vulkan", "VkPhysicalDeviceFeatures2");

        MethodSpec.Builder method = MethodSpec.methodBuilder("isSupportedBy")
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addParameter(vulkanicPhysicalDevice, "physicalDevice")
                .beginControlFlow("try ($T stack = $T.stackPush())", memoryStack, memoryStack)
                .addStatement("$T supportedFeatures2 = $T.calloc(stack).sType$$Default()", features2, features2);

        List<TypeName> uniqueStructs = features.stream()
                .map(FeatureInfo::structType)
                .distinct()
                .filter(type -> !type.toString().equals("org.lwjgl.vulkan.VkPhysicalDeviceFeatures2"))
                .toList();

        for (int i = 0; i < uniqueStructs.size(); i++) {
            TypeName structType = uniqueStructs.get(i);
            method.addStatement("$T supported$L = $T.calloc(stack).sType$$Default()", structType, i, structType);
        }

        if (!uniqueStructs.isEmpty()) {
            method.addStatement("supportedFeatures2.pNext(supported0.address())");
            for (int i = 0; i < uniqueStructs.size() - 1; i++) {
                method.addStatement("supported$L.pNext(supported$L.address())", i, i + 1);
            }
            method.addStatement("supported$L.pNext(0L)", uniqueStructs.size() - 1);
        }

        method.addStatement("$T.vkGetPhysicalDeviceFeatures2(physicalDevice.handle(), supportedFeatures2)", vk11);

        for (FeatureInfo feature : features) {
            if (feature.getter().equals("$$SPECIAL_IGNORE$$")) {
                continue;
            }

            if (feature.structType().toString().equals("org.lwjgl.vulkan.VkPhysicalDeviceFeatures2")) {
                method.beginControlFlow("if (this.$L && !supportedFeatures2.features().$L())", feature.name(), feature.getter())
                        .addStatement("return false")
                        .endControlFlow();
            } else {
                int index = uniqueStructs.indexOf(feature.structType());
                method.beginControlFlow("if (this.$L && !supported$L.$L())", feature.name(), index, feature.getter())
                        .addStatement("return false")
                        .endControlFlow();
            }
        }

        method.addStatement("return true")
                .endControlFlow();

        return method.build();
    }

    private void processVulkanExtension(Element element, TypeSpec.Builder baseClass, ClassName targetClass) {
        String name = element.getSimpleName().toString();
        VulkanExtension anno = element.getAnnotation(VulkanExtension.class);

        MethodSpec.Builder enabler = MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(targetClass)
                .addStatement("this.extensions.add($S)", anno.value())
                .addStatement("return ($T)this", targetClass);

        baseClass.addMethod(enabler.build());
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private TypeName getStructTypeName(Element e) {
        try {
            return TypeName.get(e.getAnnotation(VulkanFeature.class).featuresStruct());
        } catch (MirroredTypeException mte) {
            return TypeName.get(mte.getTypeMirror());
        }
    }
}
