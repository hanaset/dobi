package com.rufree.dobi.runner

import com.rufree.dobi.common.config.DobiJpaDatabaseConfig
import com.rufree.dobi.runner.service.NikeEventService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("local")
@SpringBootTest
@SpringBootApplication(
        scanBasePackages = [
            "com.rufree.dobi.common.*",
            "com.rufree.dobi.runner.*"
        ]
)
@ContextConfiguration(
        classes = [
            DobiJpaDatabaseConfig::class,
            AnnotationMBeanExporter::class
        ]
)
class DobiRunnerApplicationTests {

    @Autowired
    lateinit var nikeEventService: NikeEventService

    @Test
    fun 나이키_파싱_테스트() {
        nikeEventService.parseEventItem()
    }

}
