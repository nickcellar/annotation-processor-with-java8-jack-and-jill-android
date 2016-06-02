package com.example.nickwph.jackandjillannotationtest

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.transforms.JackTransform
import com.android.builder.core.JackProcessOptions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.internal.DefaultDomainObjectSet
import org.gradle.api.plugins.PluginContainer

/**
 * Created by nickwph on 5/17/16.
 */
class TestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            println '======================================'
            PluginContainer pluginContainer = project.plugins
            DefaultDomainObjectSet<BaseVariant> variants
            if (pluginContainer.hasPlugin("com.android.application") || pluginContainer.hasPlugin("com.android.test")) {
                AppExtension appExtension = project.android
                variants = appExtension.applicationVariants
            } else if (pluginContainer.hasPlugin("com.android.library")) {
                LibraryExtension libraryExtension = project.android
                variants = libraryExtension.libraryVariants
            } else {
                throw new ProjectConfigurationException("The android or android-library plugin must be applied to the project", null)
            }
            variants.all { variant ->
                File file = project.file(new File(project.buildDir, "generated/source/annotationProcessor/${variant.name}"))
                project.tasks.getByName("transformClassesWithPreJackPackagedLibrariesFor${variant.name.capitalize()}").doFirst {
                    file.mkdirs()
                }
                variant.addJavaSourceFoldersToModel(file)
                JackTransform jackTransform = variant.javaCompiler.transform
                JackProcessOptions jackProcessOptions = jackTransform.options
                jackProcessOptions.additionalParameters.put("jack.annotation-processor.source.output", file.absolutePath)
            }
            println '======================================'
        }
    }
}
