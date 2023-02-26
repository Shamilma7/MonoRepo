arrayOf("clean", "assemble", "test", "build").forEach {
    task -> tasks.register(task) {
    gradle.includedBuilds.forEach { build -> dependsOn(gradle.includedBuild(build.name).task(":${task}")) }
}
}