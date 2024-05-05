plugins {
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "com.danilovfa.discussion"
version = "0.0.1"

application {
    mainClass.set("com.danilovfa.discussion.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.cassandra)
    implementation(libs.ktor.serializationKotlinx)
    implementation(libs.logback)
    implementation(libs.kotinx.datetime)
    implementation(libs.bundles.koin)
    implementation(libs.apacheKafka)
    testImplementation(libs.ktor.server.testsJvm)
    testImplementation(libs.kotlin.junit)
}

val buildFatJar = tasks.getByName("buildFatJar")

val runDockerCompose by tasks.register<Exec>("runDockerCompose") {
    commandLine("docker", "compose", "up", "--build")
}

tasks.getByName("runDockerCompose") {
    dependsOn(buildFatJar)
}