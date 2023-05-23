rootProject.name = "Mono Repo"


pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        maven(url= "https://plugins.gradle.org/m2/")
    }
}

include(":apps")

includeBuild("./apps/backend-app-1")
includeBuild("./apps/frontend-app-1")