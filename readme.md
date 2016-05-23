# Android Annotation Processor with Java8 and Jack&Jill

Just a project to try out Android Annotation Processor in the new Java8 and Jack&Jill enviornment.

[![Build Status](https://travis-ci.org/nickwph/annotation-processor-with-java8-jack-and-jill-android.svg?branch=master)](https://travis-ci.org/nickwph/annotation-processor-with-java8-jack-and-jill-android)

## Project Set Up

### /build.gradle
```
buildscript {
    ...
    dependencies {
        classpath 'com.google.guava:guava:18.0'
        classpath 'com.android.tools.build:gradle:2.2.0-alpha1'
    }
}
```

### /app/build.gradle
```
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

## Sample Dependencies
```
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
<img width="628" alt="screen shot 2016-05-23 at 6 56 33 pm" src="https://cloud.githubusercontent.com/assets/623060/15487134/bdffbebc-2118-11e6-9416-2cbe49dff288.png">

## Change Log

### 2016/5/23 - After Google IO

- Testing code using Java8 with Annotation Processor  from
`com.android.tools.build:gradle:2.2.0-alpha1`.
- Android`N` must be targeted and latest build tool `24.0.0 rc4` must be used to work.
- It is working but generated code cannot be recognized by Android Studio.
- Extra library is needed to work -> `com.google.guava:guava:18.0`.
- Classes are generated in `build/intermediates/classes/` instead of `build/generated/source/`

