# Android Annotation Processor<br/>With Java8 and Jack&Jill [![CircleCI](https://circleci.com/gh/nickwph/annotation-processor-with-java8-jack-and-jill-android.svg?style=svg)](https://circleci.com/gh/nickwph/annotation-processor-with-java8-jack-and-jill-android)

Just a project to try out Android Annotation Processor in the new Java8 and Jack&Jill enviornment.

## Project Set Up

### /build.gradle
```groovy
buildscript {
    ...
    dependencies {
        classpath 'com.google.guava:guava:18.0' // https://code.google.com/p/android/issues/detail?id=211890
        classpath 'com.android.tools.build:gradle:2.2.0-alpha3'
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
// temporarily solution: place generated code into source directory
project.afterEvaluate {
    def variants
    if (project.plugins.hasPlugin("com.android.application") || project.plugins.hasPlugin("com.android.test")) {
        variants = project.android.applicationVariants
    } else if (project.plugins.hasPlugin("com.android.library")) {
        variants = project.android.libraryVariants
    } else {
        throw new Exception("The android application or library plugin must be applied to the project")
    }
    variants.all { variant ->
        File file = project.file(new File(project.buildDir, "generated/source/annotationProcessor/${variant.name}"))
        project.tasks.getByName("transformClassesWithPreJackPackagedLibrariesFor${variant.name.capitalize()}").doFirst {
            file.mkdirs()
        }
        variant.addJavaSourceFoldersToModel(file)
        variant.javaCompiler.transform.options.additionalParameters.put("jack.annotation-processor.source.output", file.absolutePath)
    }
}
```

## Sample Dependencies
```groovy
dependencies {
    compile 'com.google.dagger:dagger:2.2'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.2'

    compile 'com.google.auto.value:auto-value:1.2'
    annotationProcessor 'com.google.auto.value:auto-value:1.2'

    compile 'com.jakewharton:butterknife:8.0.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.0.1'

    compile 'com.bluelinelabs:logansquare:1.3.6'
    annotationProcessor 'com.bluelinelabs:logansquare-compiler:1.3.6'
}
```

## Issues

### Java 8 Stream API Not Working

- Stream api is not working working after upgrading to `2.2.0-alpha3`. 
- Uncomment [these lines](https://github.com/nickwph/annotation-processor-with-java8-jack-and-jill-android/blob/master/app/src/main/java/com/example/nickwph/jackandjillannotationtest/MainActivity.java#L45-L48) to test.
- Follow [ticket](https://code.google.com/p/android/issues/detail?id=212925).

```
:app:transformJackWithJackForDebug
Error:~/JackAndJillAnnotationTest/app/src/main/java/com/example/nickwph/jackandjillannotationtest/MainActivity.java:45: The method stream() is undefined for the type List<Integer>
Warning:~/JackAndJillAnnotationTest/app/build/generated/source/annotationProcessor/debug/com/example/nickwph/jackandjillannotationtest/MainActivity$$ViewBinder.java:23: Type safety: The constructor MainActivity$$ViewBinder.InnerUnbinder(MainActivity) belongs to the raw type MainActivity$$ViewBinder.InnerUnbinder. References to generic type MainActivity$$ViewBinder<T>.InnerUnbinder<T> should be parameterized
Warning:~/JackAndJillAnnotationTest/app/build/generated/source/annotationProcessor/debug/com/example/nickwph/jackandjillannotationtest/MainActivity$$ViewBinder.java:23: Type safety: The expression of type MainActivity$$ViewBinder.InnerUnbinder needs unchecked conversion to conform to MainActivity$$ViewBinder.InnerUnbinder<T>
Warning:~/JackAndJillAnnotationTest/app/build/generated/source/annotationProcessor/debug/com/example/nickwph/jackandjillannotationtest/MainActivity$$ViewBinder.java:23: MainActivity$$ViewBinder.InnerUnbinder is a raw type. References to generic type MainActivity$$ViewBinder<T>.InnerUnbinder<T> should be parameterized
com.android.jack.api.v01.CompilationException: Failed to compile
	at com.android.jack.api.v01.impl.Api01ConfigImpl$Api01CompilationTaskImpl.run(Api01ConfigImpl.java:109)
	at com.android.builder.core.AndroidBuilder.convertByteCodeUsingJackApis(AndroidBuilder.java:1736)
	at com.android.builder.core.AndroidBuilder.convertByteCodeUsingJack(AndroidBuilder.java:1609)
	at com.android.build.gradle.internal.transforms.JackTransform.runJack(JackTransform.java:222)
	at com.android.build.gradle.internal.transforms.JackTransform.transform(JackTransform.java:196)
	at com.android.build.gradle.internal.pipeline.TransformTask$2.call(TransformTask.java:170)
	at com.android.build.gradle.internal.pipeline.TransformTask$2.call(TransformTask.java:166)
	at com.android.builder.profile.ThreadRecorder$1.record(ThreadRecorder.java:55)
	at com.android.builder.profile.ThreadRecorder$1.record(ThreadRecorder.java:47)
	at com.android.build.gradle.internal.pipeline.TransformTask.transform(TransformTask.java:165)
    ...
Error:com.android.jack.frontend.FrontendCompilationException: Failed to compile
	at com.android.jack.Jack.buildSession(Jack.java:895)
	at com.android.jack.Jack.run(Jack.java:475)
	at com.android.jack.api.v01.impl.Api01ConfigImpl$Api01CompilationTaskImpl.run(Api01ConfigImpl.java:102)
	... 93 more
Error:Execution failed for task ':app:transformJackWithJackForDebug'.
> com.android.build.api.transform.TransformException: com.android.jack.api.v01.CompilationException: Failed to compile
```

### Incorrect location for code generation
- **This issue has a temporary solution [here](https://github.com/nickwph/annotation-processor-with-java8-jack-and-jill-android/blob/master/app/build.gradle#L56-L78).** 
- Classes are generated in `build/intermediates/classes/` instead of `build/generated/source/`, so they are not treated as source by Android Studio. Code referencing them will be displayed red.
- <img width="628" alt="screen shot 2016-05-23 at 6 56 33 pm" src="https://cloud.githubusercontent.com/assets/623060/15487134/bdffbebc-2118-11e6-9416-2cbe49dff288.png">

## Change Log

### 2016/6/13 - Android Plugin Updated

- Updated android plugin version to `com.android.tools.build:gradle:2.2.0-alpha3`.
- Java 8 stream api is no longer working.
- [Ticket](https://code.google.com/p/android/issues/detail?id=212925) filed.

### 2016/6/2 - Temporary Solution
- Added script to temporarily place generated code into source directory.
- See here: [/app/build.gradle](https://github.com/nickwph/annotation-processor-with-java8-jack-and-jill-android/blob/master/app/build.gradle#L56-L78)
- Alternative with gradle plugin. See changes in a [pull request](https://github.com/nickwph/annotation-processor-with-java8-jack-and-jill-android/pull/3)

### 2016/6/1 - Android Plugin Updated

- Updated android plugin version to `com.android.tools.build:gradle:2.2.0-alpha2`.
- Extra dependency `com.google.guava:guava:18.0` is still neccessary. [Ticket](https://code.google.com/p/android/issues/detail?id=211890) filed.
- Classes are generated in a bad location `build/intermediates/classes/`

### 2016/5/23 - After Google IO

- Testing code using Java8 with Annotation Processor from `com.android.tools.build:gradle:2.2.0-alpha1`.
- Android`N` must be targeted and latest build tool `24.0.0 rc4` must be used to work.
- It is working but generated code cannot be recognized by Android Studio.
- Extra library is needed to work -> `com.google.guava:guava:18.0`.
- Classes are generated in `build/intermediates/classes/` instead of `build/generated/source/`

