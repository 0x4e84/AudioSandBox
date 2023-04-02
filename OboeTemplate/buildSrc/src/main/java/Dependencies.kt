object Versions {
    const val gradle_tools                = "8.1.0-alpha11"  // https://mvnrepository.com/artifact/com.android.tools.build/gradle

    // Android Support
    const val support                     = "28.0.0"         // https://mvnrepository.com/artifact/com.android.support
    const val support_multidex            = "2.0.1"          // https://mvnrepository.com/artifact/androidx.multidex/multidex
    const val x_appcompat                 = "1.3.1"
    const val x_constraint_layout         = "2.1.0"
    const val x_coordinator_layout        = "1.1.0"
    const val x_core_ktx                  = "1.6.0"
    const val x_junit                     = "1.1.2"
    const val x_test                      = "1.4.0"
    const val x_test_espresso             = "3.4.0"

    // Google
    const val gson                        = "2.8.7"           // https://mvnrepository.com/artifact/com.google.code.gson/gson
    const val gms_play_services           = "12.0.0"         // https://mvnrepository.com/artifact/com.google.android.gms

    // Jetbrains
    const val kotlin                      = "1.8.20"          // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib
    const val kotlinx_coroutines          = "1.6.4"           // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core

    // Others
    const val crashlytics                 = "2.9.6"          // https://mvnrepository.com/artifact/com.crashlytics.sdk.android/crashlytics
    const val crashlytics_answers         = "1.4.3"          // https://mvnrepository.com/artifact/com.crashlytics.sdk.android/answers
    const val junit                       = "4.12"           // https://mvnrepository.com/artifact/junit/junit
    const val junit_jupiter_api           = "5.3.1"          // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
}

object Libs {
    const val gradle_build_tools          = "com.android.tools.build:gradle:${Versions.gradle_tools}"
    const val junit_runner                = "android.support.test.runner.AndroidJUnitRunner"

    // Android Support
    const val x_appcompat                 = "androidx.appcompat:appcompat:${Versions.x_appcompat}"
    const val x_constraint_layout         = "androidx.constraintlayout:constraintlayout:${Versions.x_constraint_layout}"
    const val x_coordinator_layout        = "androidx.coordinatorlayout:coordinatorlayout:${Versions.x_coordinator_layout}"
    const val x_core_ktx                  = "androidx.core:core-ktx:${Versions.x_core_ktx}"
    const val x_espresso                  = "androidx.test.espresso:espresso-core:${Versions.x_test_espresso}"
    const val x_junit                     = "androidx.test.ext:junit:${Versions.x_junit}"
    const val support_appcompat_v7        = "com.android.support:appcompat-v7:${Versions.support}"
    const val support_design              = "com.android.support:design:${Versions.support}"
    const val support_multidex            = "com.android.support:multidex:${Versions.support_multidex}"

    // Google
    const val gson                        = "com.google.code.gson:gson:${Versions.gson}"
    const val play_services               = "com.google.android.gms:play-services:${Versions.gms_play_services}"

    // Jetbrains
    const val kotlin_plugin               = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    // Others
    const val crahslytics                 = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"
    const val crahslytics_answers         = "com.crashlytics.sdk.android:answers:${Versions.crashlytics_answers}"
    const val junit                       = "junit:junit:${Versions.junit}"
    const val junit_jupiter_api           = "org.junit.jupiter:junit-jupiter-api:${Versions.junit_jupiter_api}"
}
