package twitter.challenge.espenia.core.domain;

import lombok.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@ToString
@Builder
public class TimelineCache {
    private final String id;
    private final String userId;
    private final List<String> tweetIds;
    private final Date lastUpdated;
}
