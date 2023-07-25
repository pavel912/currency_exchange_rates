plugins {
    id("java")
    id("application")
}

group = "pavel912.cer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("info.picocli:picocli:4.6.3")
    implementation("com.google.guava:guava:30.1.1-jre")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("pavel912.cer.Main")
}