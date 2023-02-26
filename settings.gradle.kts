rootProject.name = "Translator Mono Repo"


pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven(url= "https://plugins.gradle.org/m2/")
    }
}

include(":apps")

includeBuild("./apps/russian-to-chechen")