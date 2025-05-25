package twitter.challenge.espenia.core.domain;

import lombok.*;
import java.util.Date;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@ToString
@Builder
public class Follow {
    private final String id;
    private final String followerId;
    private final String followedId;
    private final Date createdAt;
}
