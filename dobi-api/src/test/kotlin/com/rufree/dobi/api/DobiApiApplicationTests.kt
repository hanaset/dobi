package com.rufree.dobi.api

import com.rufree.dobi.common.config.DobiJpaDatabaseConfig
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
            "com.rufree.dobi.api.*"
        ]
)
@ContextConfiguration(
        classes = [
            DobiJpaDatabaseConfig::class,
            AnnotationMBeanExporter::class
        ]
)
class DobiApiApplicationTests {


}
