object DependencyVersions {
    const val SWAGGER_VERSION = "2.9.2"
}

dependencies {

    implementation(project(":dobi-common"))

    //swagger
    implementation("io.springfox:springfox-swagger2:${DependencyVersions.SWAGGER_VERSION}")
    implementation("io.springfox:springfox-swagger-ui:${DependencyVersions.SWAGGER_VERSION}")

    // cache
    implementation("org.ehcache:ehcache:3.8.1")
    implementation("org.springframework.boot:spring-boot-starter-cache:2.3.4.RELEASE")
    implementation("javax.cache:cache-api:1.1.1")

    // jsoup
    implementation("org.jsoup:jsoup:1.13.1")

    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Telegram BOT API
    implementation("org.telegram:telegrambots:5.2.0")

    // Html Simulater
    implementation("net.sourceforge.htmlunit:htmlunit:2.52.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

springBoot.buildInfo { properties { } }

configurations {
    val archivesBaseName = "dobi-api-staging"
}