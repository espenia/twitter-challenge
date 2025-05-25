package twitter.challenge.espenia.core.domain;

import lombok.*;
import java.util.Date;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@ToString
@Builder
public class Tweet {
    private final String id;
    private final String userId;
    private final String content;
    private final Date createdAt;
    
    @Setter
    private long likeCount;
}
