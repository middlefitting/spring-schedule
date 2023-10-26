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

  /**
   * 비디오 업로드 요청, 병목 요청의 예시
   *
   * @param videoName
   * @return
   */
  @PostMapping("/video/upload")
  public ResponseEntity<String> videoRequest(@RequestParam String videoName) {
    try {
      Thread.sleep(1000 * 10);
      System.out.println(videoName + "영상의 Youtube 화질 처리를 완료하였습니다!");
    } catch (InterruptedException e) {
      return ResponseEntity.status(500).body("내부 서버 오류가 발생하였습니다! \uD83D\uDE4F");
    }
    return ResponseEntity.ok("요청이 완료되었습니다!");
  }

  /**
   * 비디오 업로드 요청, 비동기 Task 활용
   *
   * @param videoName
   * @return
   */
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
