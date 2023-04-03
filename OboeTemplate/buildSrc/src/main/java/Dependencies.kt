object Versions {
    const val gradle_tools = "8.1.0-alpha11" // https://mvnrepository.com/artifact/com.android.tools.build/gradle

    // Android Support
    const val x_appcompat = "1.3.1"
    const val x_constraint_layout = "2.1.0"

    // Jetbrains
    const val kotlin = "1.8.20" // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib

    // Others
    const val junit = "4.12" // https://mvnrepository.com/artifact/junit/junit
}

object Libs {
    const val gradle_build_tools = "com.android.tools.build:gradle:${Versions.gradle_tools}"
    const val junit_runner = "android.support.test.runner.AndroidJUnitRunner"

    // Android Support
    const val x_appcompat = "androidx.appcompat:appcompat:${Versions.x_appcompat}"
    const val x_constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.x_constraint_layout}"

    // Jetbrains
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val junit = "junit:junit:${Versions.junit}"
}
