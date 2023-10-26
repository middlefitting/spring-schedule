package com.example.schedule.executor;

/**
 * TaskExecutorExample.
 *
 * <p>
 *
 * </p>
 *
 * @author : middlefitting
 * @see :
 * @since : 2023/10/26
 */

import static java.lang.Thread.sleep;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutorExample {

  private class UploadVideoTask implements Runnable {

    private String videoName;

    public UploadVideoTask(String videoName) {
      this.videoName = videoName;
    }

    public void run() {
      try {
        Thread.sleep(1000 * 5);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println(videoName + "영상의 Youtube 화질 처리를 완료하였습니다!");
    }
  }

  private final TaskExecutor taskExecutor;

  public TaskExecutorExample(@Qualifier("youtubeTaskExecutor") TaskExecutor taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

  public void uploadVideo(String videoName) throws InterruptedException {
    taskExecutor.execute(new UploadVideoTask(videoName));
  }
}