# Android Annotation Processor<br/>With Java8 and Jack&Jill [![CircleCI](https://circleci.com/gh/nickwph/annotation-processor-with-java8-jack-and-jill-android.svg?style=svg)](https://circleci.com/gh/nickwph/annotation-processor-with-java8-jack-and-jill-android)

Just a project to try out Android Annotation Processor in the new Java8 and Jack&Jill enviornment.

## Project Set Up

### /build.gradle
```groovy
buildscript {
    ...
    dependencies {
        classpath 'com.google.guava:guava:18.0'
        classpath 'com.android.tools.build:gradle:2.2.0-alpha2'
    }
}
```

### /app/build.gradle
```groovy
android {
    compileSdkVersion 'android-N'
    buildToolsVersion '24.0.0 rc4'
    defaultConfig {
        ...
        targetSdkVersion 'N'
        jackOptions {
            enabled true
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    ...
}
```
```groovy
// temporarily place generated code into source directory
project.afterEvaluate {
    def pluginContainer = project.plugins
    def variants
    if (pluginContainer.hasPlugin("com.android.application") || pluginContainer.hasPlugin("com.android.test")) {
        def appExtension = project.android
        variants = appExtension.applicationVariants
    } else if (pluginContainer.hasPlugin("com.android.library")) {
        def libraryExtension = project.android
        variants = libraryExtension.libraryVariants
    } else {
        throw new Exception("The android application or library plugin must be applied to the project")
    }
    variants.all { variant ->
        File file = project.file(new File(project.buildDir, "generated/source/annotationProcessor/${variant.name}"))
        project.tasks.getByName("transformClassesWithPreJackPackagedLibrariesFor${variant.name.capitalize()}").doFirst {
            file.mkdirs()
        }
        variant.addJavaSourceFoldersToModel(file)
        def jackTransform = variant.javaCompiler.transform
        def jackProcessOptions = jackTransform.options
        jackProcessOptions.additionalParameters.put("jack.annotation-processor.source.output", file.absolutePath)
    }
}
```

## Sample Dependencies
```groovy
dependencies {
    compile 'com.google.dagger:dagger:2.2'
    compile 'javax.annotation:jsr250-api:1.0'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.2'

    compile 'com.bluelinelabs:logansquare:1.3.6'
    annotationProcessor 'com.bluelinelabs:logansquare-compiler:1.3.6'

    compile 'com.google.auto.value:auto-value:1.2'
    annotationProcessor 'com.google.auto.value:auto-value:1.2'

    compile 'com.jakewharton:butterknife:8.0.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.0.1'

    compile 'com.bluelinelabs:logansquare:1.3.6'
    annotationProcessor 'com.bluelinelabs:logansquare-compiler:1.3.6'
}
```

## Issues

- Classes are generated in `build/intermediates/classes/` instead of `build/generated/source/`, so they are not treated as source by Android Studio. Code referencing them will be displayed red.
- This issue is 'kinda' fixed temporary with a script: https://github.com/nickwph/annotation-processor-with-java8-jack-and-jill-android/blob/master/app/build.gradle#L56-L78
<img width="628" alt="screen shot 2016-05-23 at 6 56 33 pm" src="https://cloud.githubusercontent.com/assets/623060/15487134/bdffbebc-2118-11e6-9416-2cbe49dff288.png">

## Change Log

### 2016/6/2 - Temporary Fix
- Added script to temporarily place generated code into source directory.
- See here: [/app/build.gradle](https://github.com/nickwph/annotation-processor-with-java8-jack-and-jill-android/blob/master/app/build.gradle#L56-L78)

### 2016/6/1 - Android Plugin Updated

- Updated android plugin version to `com.android.tools.build:gradle:2.2.0-alpha2`.
- Extra dependency `com.google.guava:guava:18.0` is still neccessary.
- Classes are generated in a bad location `build/intermediates/classes/`

### 2016/5/23 - After Google IO

- Testing code using Java8 with Annotation Processor from `com.android.tools.build:gradle:2.2.0-alpha1`.
- Android`N` must be targeted and latest build tool `24.0.0 rc4` must be used to work.
- It is working but generated code cannot be recognized by Android Studio.
- Extra library is needed to work -> `com.google.guava:guava:18.0`.
- Classes are generated in `build/intermediates/classes/` instead of `build/generated/source/`

