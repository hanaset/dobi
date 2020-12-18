package com.rufree.dobi.api.config.scheduler

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
@EnableScheduling
class SchedulerConfig: SchedulingConfigurer {

    override fun configureTasks(scheduledTaskRegistrar: ScheduledTaskRegistrar) {
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()
        threadPoolTaskScheduler.poolSize = 30
        threadPoolTaskScheduler.setThreadNamePrefix("dobiScheduler-")
        threadPoolTaskScheduler.initialize()
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler)
    }
}