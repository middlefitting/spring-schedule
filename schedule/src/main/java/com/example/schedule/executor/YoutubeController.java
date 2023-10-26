package com.example.schedule.executor;

import java.util.concurrent.RejectedExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * YoutubeController.
 *
 * <p>
 *
 * </p>
 *
 * @author : middlefitting
 * @see :
 * @since : 2023/10/26
 */
@RestController
@RequestMapping("/youtube")
public class YoutubeController {

  private final TaskExecutorExample taskExecutorExample;

  public YoutubeController(TaskExecutorExample taskExecutorExample) {
    this.taskExecutorExample = taskExecutorExample;
  }

  @PostMapping("/video")
  public ResponseEntity<String> video(@RequestParam String videoName) {
    try {
      // 비동기 테스크 처리
      taskExecutorExample.uploadVideo(videoName);
    } catch (RejectedExecutionException | InterruptedException e) {
      // 비동기 테스크 처리가 거부되었을 때 (스레드 풀이 가득차거나 스레드 풀이 종료되었을 때)
      return ResponseEntity.status(503).body("요청이 많아 처리할 수 없습니다. 잠시 후 다시 시도해주세요. \uD83D\uDE4F");
    }
    // 비동기 테스크 처리가 거부되지 않았다면 성공 응답을 반환
    return ResponseEntity.ok("요청이 완료되었습니다! 최대 약 1분 소요 예정!");
  }
}
