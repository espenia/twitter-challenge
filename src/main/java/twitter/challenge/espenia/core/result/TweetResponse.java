package twitter.challenge.espenia.core.result;

import lombok.Builder;
import java.util.Date;

@Builder
public record TweetResponse(
    String id,
    String userId,
    String content,
    Date createdAt,
    Long likeCount
) {}
