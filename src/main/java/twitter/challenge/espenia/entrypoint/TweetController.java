package twitter.challenge.espenia.entrypoint;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import twitter.challenge.espenia.core.usecase.CreateTweet;
import twitter.challenge.espenia.core.usecase.request.TweetRequest;
import twitter.challenge.espenia.core.result.TweetResponse;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    private final CreateTweet createTweet;

    @Autowired
    public TweetController(final CreateTweet createTweet) {
        this.createTweet = createTweet;
    }

    /**
     * Create a new tweet
     * 
     * @param tweetRequest The tweet data
     * @return The created tweet
     */
    @PostMapping
    public ResponseEntity<TweetResponse> createTweet(@Valid @RequestBody final TweetRequest tweetRequest) {
        TweetResponse createdTweet = createTweet.execute(tweetRequest);
        return new ResponseEntity<>(createdTweet, HttpStatus.CREATED);
    }
}
