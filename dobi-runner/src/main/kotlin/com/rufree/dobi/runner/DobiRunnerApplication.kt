package com.rufree.dobi.runner

import com.rufree.dobi.common.config.DobiJpaDatabaseConfig
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.info.BuildProperties
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*
import javax.annotation.PostConstruct

@EnableScheduling
@SpringBootApplication(scanBasePackages = [
    "com.rufree.dobi.common.*",
    "com.rufree.dobi.runner.*"
])
@Import(DobiJpaDatabaseConfig::class)
class DobiRunnerApplication(
        private val buildProperties: BuildProperties,
        private val environment: Environment
) : ApplicationListener<ApplicationReadyEvent> {

    private val logger = LoggerFactory.getLogger(DobiRunnerApplication::class.java)

    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        logger.info("{} applicationReady, profiles = {}", buildProperties.name, environment.activeProfiles.contentToString())
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(DobiRunnerApplication::class.java)
            .listeners(ApplicationPidFileWriter())
            .run(*args)
}