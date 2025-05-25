package twitter.challenge.espenia.infra.mongodb.document;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Document(collection = "timeline_cache")
@NoArgsConstructor
@AllArgsConstructor
public class TimelineCacheDocument extends BaseDocument {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String userId;
    
    private List<String> tweetIds;
    
    @Indexed
    @LastModifiedDate
    private Date lastUpdated;
}
