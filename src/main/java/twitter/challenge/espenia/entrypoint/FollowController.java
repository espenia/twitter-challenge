package twitter.challenge.espenia.entrypoint;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import twitter.challenge.espenia.core.result.FollowResponse;
import twitter.challenge.espenia.core.usecase.FindFollowersUseCase;
import twitter.challenge.espenia.core.usecase.FollowUseCase;
import twitter.challenge.espenia.core.usecase.UnFollowUseCase;
import twitter.challenge.espenia.core.usecase.request.FollowRequest;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    private final FollowUseCase followUseCase;
    private final UnFollowUseCase unFollowUseCase;
    private final FindFollowersUseCase findFollowersUseCase;

    @Autowired
    public FollowController(final FollowUseCase followUseCase,
                           final UnFollowUseCase unFollowUseCase,
                           final FindFollowersUseCase findFollowersUseCase) {
        this.followUseCase = followUseCase;
        this.unFollowUseCase = unFollowUseCase;
        this.findFollowersUseCase = findFollowersUseCase;
    }    /**
     * Create a follow relationship
     * 
     * @param followerId The ID of the user who is following
     * @param followRequest The follow request data
     * @return The created follow relationship
     */
    @PostMapping("/{followerId}")
    public ResponseEntity<FollowResponse> createFollow(
            @PathVariable final String followerId,
            @Valid @RequestBody final FollowRequest followRequest) {
        FollowResponse createdFollow = followUseCase.execute(followerId, followRequest);
        return new ResponseEntity<>(createdFollow, HttpStatus.CREATED);
    }    /**
     * Delete a follow relationship (unfollow)
     * 
     * @param followerId The ID of the user who is following
     * @param followedId The ID of the user being followed
     * @return No content
     */
    @DeleteMapping("/{followerId}/{followedId}")
    public ResponseEntity<Void> deleteFollow(
            @PathVariable final String followerId,
            @PathVariable final String followedId) {
        unFollowUseCase.execute(followerId, followedId);
        return ResponseEntity.noContent().build();
    }    /**
     * Get all followers of a user
     * 
     * @param userId The user ID
     * @return List of followers
     */
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FollowResponse>> getFollowers(@PathVariable final String userId) {
        List<FollowResponse> followers = findFollowersUseCase.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }    /**
     * Get all users that a user is following
     * 
     * @param userId The user ID
     * @return List of users being followed
     */
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<FollowResponse>> getFollowing(@PathVariable final String userId) {
        List<FollowResponse> following = findFollowersUseCase.getFollowing(userId);
        return ResponseEntity.ok(following);
    }
}
