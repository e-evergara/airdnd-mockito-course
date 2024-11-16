plugins {
    id("java")
}

group = "org.hsa.airdnd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter:5.10.0")
    testImplementation("org.mockito:mockito-core:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}