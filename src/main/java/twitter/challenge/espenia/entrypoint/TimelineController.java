package twitter.challenge.espenia.entrypoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import twitter.challenge.espenia.core.result.TimelineCacheResponse;
import twitter.challenge.espenia.core.usecase.GetTimelineUseCase;

@Slf4j
@RestController
@RequestMapping("/api/timeline")
public class TimelineController {

    private final GetTimelineUseCase getTimelineUseCase;

    @Autowired
    public TimelineController(final GetTimelineUseCase getTimelineUseCase) {
        this.getTimelineUseCase = getTimelineUseCase;
    }

    /**
     * Get a user's timeline
     * 
     * @param userId The ID of the user
     * @return The user's timeline with tweets
     */
    @GetMapping("/{userId}")
    public ResponseEntity<TimelineCacheResponse> getTimeline(@PathVariable final String userId) {
        log.info("Received request to get timeline for user: {}", userId);
        TimelineCacheResponse timeline = getTimelineUseCase.execute(userId);
        log.info("Returning timeline with {} tweets for user: {}", timeline.tweets().size(), userId);
        return ResponseEntity.ok(timeline);
    }
}
