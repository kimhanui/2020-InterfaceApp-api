package com.infe.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 기본적으로 모든 @Scheduled 작업은 Spring에 의해 생성 된 한개의 스레드 풀에서 실행된다.
 * 따라서 현재 돌고있는 thread가 끝나야 다음 스케쥴러가 동작할 수 있다.
 * 아래 설정을 통해 Schedule에 대한 쓰래드 풀을 생성하고, 그 쓰레드 풀을 사용하여 모든 스케줄 된 작업을 실행하도록 할 수 있다.
 * */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    private final int POOL_SIZE = 10;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
        threadPoolTaskScheduler.initialize();
        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}