plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "kafka-processing-obj"
include("src:test:kafka-processing-obj")
findProject(":src:test:kafka-processing-obj")?.name = "kafka-processing-obj"
