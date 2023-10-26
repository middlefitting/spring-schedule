package com.example.schedule.executor;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * TaskConfig.
 *
 * <p>
 *
 * </p>
 *
 * @author : middlefitting
 * @see :
 * @since : 2023/10/26
 */
@Configuration
@EnableAsync
public class TaskConfig {

  /**
   * 비동기 처리를 위한 ThreadPoolTaskExecutor 빈 등록
   * @return TaskExecutor
   */
  @Bean(name = "youtubeTaskExecutor")
  public TaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    /**
     * 코어 스레드 수
     * 기본적으로 유지하는 스레드 수
     */
    executor.setCorePoolSize(2);
    /**
     * 최대 스레드 수
     * 코어 스레드 수를 넘어서는 스레드 수, 최대 스레드 수
     */
    executor.setMaxPoolSize(2);
    /**
     * 큐에 대기하는 최대 메시지 수
     * 코어 스레드가 모두 작업 중일 때 대기
     */
    executor.setQueueCapacity(2);

    /**
     * 거부 정책 설정
     */
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

    /**
     * initialize() 메서드를 호출하여 초기화
     */
    executor.initialize();
    return executor;
  }
}
