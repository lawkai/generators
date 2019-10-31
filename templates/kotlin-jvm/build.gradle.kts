import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    application
    kotlin("jvm") version "1.3.50"
    id("org.jetbrains.dokka") version "0.9.17"
}

application {
    group = "{{groupId}}"
    version = project.version
    applicationName = "{{artifactId}}"
    mainClassName = "{{groupId}}.{{artifactId}}.AppKt"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2"))

    val junit_version = "5.4.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit_version")

    implementation("org.slf4j:slf4j-api:1.7.28")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    testImplementation(kotlin("test"))
    testRuntimeOnly(kotlin("reflect"))
}

// setup the test task
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.allWarningsAsErrors = false
    kotlinOptions.apiVersion = "1.3"
    kotlinOptions.languageVersion = "1.3"
    kotlinOptions.jvmTarget = "1.8"
}

val uberJar by tasks.creating(Jar::class) {
    archiveClassifier.set("uber")
    manifest {
        attributes["Main-Class"] = application.mainClassName
        attributes["Implementation-Version"] = project.version
        attributes["Implementation-Title"] = project.name
    }
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

val sourcesJar by tasks.creating(Jar::class) {
    description = "Assembles sources JAR"
    archiveClassifier.set("sources")
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    from(sourceSets.main.get().allSource)
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "${buildDir}/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
    description = "Assembles Kotlin docs JAR with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["kotlin"])
            artifact(uberJar)
            artifact(sourcesJar)
            artifact(dokkaJar)

            // versionMapping is not really needed for uberjar. Useful for resolving dynamice dependencies for maven
            // https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }

    repositories {
        // publish to local
        maven {
            url = uri("${buildDir}/repository")
        }
    }
}
