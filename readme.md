# Android Annotation Processor<br/>With Java8 and Jack&Jill [![CircleCI](https://circleci.com/gh/nickwph/annotation-processor-with-java8-jack-and-jill-android.svg?style=svg)](https://circleci.com/gh/nickwph/annotation-processor-with-java8-jack-and-jill-android)

Just a project to try out Android Annotation Processor in the new Java8 and Jack&Jill enviornment. Also check if libraries and testing frameworks work with it.

#### Supported tools
- [ ] Instant Run
- [ ] Data Binding (`android.dataBinding.enabled = true` will fail)
- [ ] Minifying For Tests (Enable proguard for `release` and set `testBuildType 'release'` will fail)

#### Libraries
- [x] Dagger 2.4
- [x] Dagger 2.5 (`classpath 'com.google.guava:guava:19.0'` needed)
- [x] AutoValue 1.2
- [x] Butterknife 8.1.0
- [x] LoganSquare 1.3.6

#### Testing frameworks
- [x] JUnit 4.12
- [x] Robolectric 3.1
- [x] Mockito 1.10.19

## Project Set Up

#### /build.gradle
```groovy
buildscript {
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.0-alpha4'
    }
}
```

#### /app/build.gradle
```groovy
android {
    compileSdkVersion 24
    buildToolsVersion '24.0.0'
    defaultConfig {
        targetSdkVersion 24
        jackOptions {
            enabled true
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

## Sample Dependencies
```groovy
dependencies {
    // dagger 2
    compile 'com.google.dagger:dagger:2.5'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.5'
    // auto-value
    compile 'com.google.auto.value:auto-value:1.2'
    annotationProcessor 'com.google.auto.value:auto-value:1.2'
    // butterknife
    compile 'com.jakewharton:butterknife:8.1.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.1.0'
    // logan square
    compile 'com.bluelinelabs:logansquare:1.3.6'
    annotationProcessor 'com.bluelinelabs:logansquare-compiler:1.3.6'
}
```

## Issues

#### Stream API Only Supported In Android N
It is only supported if you set `minSdkVersion` to `24` or above.

#### Instant Run Not Supported
It is simply not supported now. Notification will be shown whenever you try to use it.
<img width="628" src="https://cloud.githubusercontent.com/assets/623060/16383564/9d0fae00-3c53-11e6-977d-90b5d0c7b9a1.png">

#### Data Binding Not Supported
If you put the following, error will be thrown.
```groovy
android {
    dataBinding {
        enabled = true // Error: Data Binding does not support Jack builds yet
    }
}
```

#### Minifying For Tests Not Supported
If you put the following, error will be thrown.
```
Error:A problem occurred configuring project ':app'.
> Minifying the variant used for tests is not supported when using Jack.
```
```groovy
android {
    testBuildType 'release'
    buildTypes {
        release {
            minifyEnabled true
            signingConfig signingConfigs.debug
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}
```

#### ~~Java 8 Stream API Not Working~~ (fixed since `2.2.0-alpha4`)
~~Stream api is not working working after upgrading to `2.2.0-alpha3`, follow [ticket](https://code.google.com/p/android/issues/detail?id=212925). It means the following doesn't work:~~
```
Arrays.asList(3, 1, 2).stream()
    .sorted()
    .map(String::valueOf)
    .forEach(integer -> view.setText(view.getText() + " => " + integer));
```

#### ~~Incorrect location for code generation~~ (fixed since `2.2.0-alpha4`)
~~Classes are generated in `build/intermediates/classes/` instead of `build/generated/source/`, so they are not treated as source by Android Studio. Code referencing them will be displayed red. **This issue has a [temporary solution](https://gist.github.com/nickwph/fac980fd6cf4ef9415d5a35477646024)**~~
<img width="628" src="https://cloud.githubusercontent.com/assets/623060/15487134/bdffbebc-2118-11e6-9416-2cbe49dff288.png">

## Change Log

#### 2016/6/25 - Android Plugin Updated

- Updated android plugin version to `com.android.tools.build:gradle:2.2.0-alpha4`.
- Extra dependency `com.google.guava:guava:18.0` is no longer needed
- Java 8 stream api is fixed.
- Classes are generated in `build/generated/source/apt` now.
- `compileSdkVersion` is updated to `24`
- `buildToolsVersion` is updated to `24.0.0`
- `targetSdkVersion` is updated to `24`

#### 2016/6/13 - Android Plugin Updated

- Updated android plugin version to `com.android.tools.build:gradle:2.2.0-alpha3`.
- Java 8 stream api is no longer working.
- [Ticket](https://code.google.com/p/android/issues/detail?id=212925) filed.

#### 2016/6/2 - Temporary Solution
- Added script to temporarily place generated code into source directory.
- See here: [gist](https://gist.github.com/nickwph/fac980fd6cf4ef9415d5a35477646024)
- Alternative with gradle plugin. See changes in a [pull request](https://github.com/nickwph/annotation-processor-with-java8-jack-and-jill-android/pull/3)

#### 2016/6/1 - Android Plugin Updated

- Updated android plugin version to `com.android.tools.build:gradle:2.2.0-alpha2`.
- Extra dependency `com.google.guava:guava:18.0` is still neccessary. [Ticket](https://code.google.com/p/android/issues/detail?id=211890) filed.
- Classes are generated in a bad location `build/intermediates/classes/`

#### 2016/5/23 - After Google IO

- Testing code using Java8 with Annotation Processor from `com.android.tools.build:gradle:2.2.0-alpha1`.
- Android`N` must be targeted and latest build tool `24.0.0 rc4` must be used to work.
- It is working but generated code cannot be recognized by Android Studio.
- Extra library is needed to work -> `com.google.guava:guava:18.0`.
- Classes are generated in `build/intermediates/classes/` instead of `build/generated/source/`

