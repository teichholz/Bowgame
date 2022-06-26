plugins {
    id("com.utopia-rise.godot-kotlin-jvm") version "0.3.4-3.4.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.utopia-rise:godot-library:0.3.4-3.4.4")
    implementation("joda-time:joda-time:2.10.6")
}

godot {
    isAndroidExportEnabled.set(false)
    d8ToolPath.set(File("${System.getenv("ANDROID_SDK_ROOT")}/build-tools/31.0.0/d8"))
    androidCompileSdkDir.set(File("${System.getenv("ANDROID_SDK_ROOT")}/platforms/android-30"))
}
