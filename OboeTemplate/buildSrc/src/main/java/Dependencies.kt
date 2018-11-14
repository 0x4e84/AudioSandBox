object Versions {
    const val gradle_tools            = "3.4.0-alpha03"  // https://mvnrepository.com/artifact/com.android.tools.build/gradle

    // Android Support
    const val support                 = "28.0.0"         // https://mvnrepository.com/artifact/com.android.support
    const val support_constraint      = "2.0.0-alpha2"   // https://mvnrepository.com/artifact/com.android.support.constraint
    const val support_multidex        = "1.0.3"          // https://mvnrepository.com/artifact/com.android.support
    const val support_test            = "1.0.2"          // https://mvnrepository.com/artifact/com.android.support.test
    const val support_test_espresso   = "3.0.2-alpha1"   // https://mvnrepository.com/artifact/com.android.support.test.espresso

    // Google
    const val gson                    = "2.8.5"          // https://mvnrepository.com/artifact/com.google.code.gson
    const val gms_play_services       = "12.0.0"         // https://mvnrepository.com/artifact/com.google.android.gms

    // Jetbrains
    const val jetbrains_annotations   = "16.0.3"         // https://mvnrepository.com/artifact/org.jetbrains/annotations-java5
    const val kotlin                  = "1.3.0"          // https://mvnrepository.com/artifact/org.jetbrains.kotlin

    // Others
    const val crashlytics             = "2.9.6"          // https://mvnrepository.com/artifact/com.crashlytics.sdk.android/crashlytics
    const val crashlytics_answers     = "1.4.3"          // https://mvnrepository.com/artifact/com.crashlytics.sdk.android/answers
    const val junit                   = "4.12"           // https://mvnrepository.com/artifact/junit/junit
    const val junit_jupiter_api       = "5.3.1"          // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
}

object Libs {
    const val gradle_build_tools      = "com.android.tools.build:gradle:${Versions.gradle_tools}"
    const val junit_runner            = "android.support.test.runner.AndroidJUnitRunner"

    // Android Support
    const val constraint_layout       = "com.android.support.constraint:constraint-layout:${Versions.support_constraint}"
    const val support_appcompat_v7    = "com.android.support:appcompat-v7:${Versions.support}"
    const val support_design          = "com.android.support:design:${Versions.support}"
    const val support_espresso        = "com.android.support.test.espresso:espresso-core:${Versions.support_test_espresso}"
    const val support_multidex        = "com.android.support:multidex:${Versions.support_multidex}"
    const val support_test_rules      = "com.android.support.test:rules:${Versions.support_test}"
    const val support_test_runner     = "com.android.support.test:runner:${Versions.support_test}"

    // Google
    const val gson                    = "com.google.code.gson:gson:${Versions.gson}"
    const val play_services           = "com.google.android.gms:play-services:${Versions.gms_play_services}"

    // Jetbrains
    const val jetbrains_annotations   = "org.jetbrains:annotations-java5:${Versions.jetbrains_annotations}"
    const val kotlin_plugin           = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    // Others
    const val crahslytics             = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"
    const val crahslytics_answers     = "com.crashlytics.sdk.android:answers:${Versions.crashlytics_answers}"
    const val junit                   = "junit:junit:${Versions.junit}"
    const val junit_jupiter_api       = "org.junit.jupiter:junit-jupiter-api:${Versions.junit_jupiter_api}"
}
