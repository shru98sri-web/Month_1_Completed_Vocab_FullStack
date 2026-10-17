allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

val newBuildDir: Directory =
    rootProject.layout.buildDirectory
        .dir("../../build")
        .get()
rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    val newSubprojectBuildDir: Directory = newBuildDir.dir(project.name)
    project.layout.buildDirectory.value(newSubprojectBuildDir)
}

// Fixed & modernized subprojects block
subprojects {
    afterEvaluate {
        extensions.findByType(com.android.build.gradle.api.AndroidBasePlugin::class.java)?.let {
            val android = extensions.findByName("android") as? com.android.build.gradle.BaseExtension
            android?.apply {
                compileSdkVersion(34)
                defaultConfig {
                    targetSdkVersion(34)
                }
            }
        }
    }
    configurations.all {
        exclude(group = "com.google.firebase", module = "firebase-iid")
    }

}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
