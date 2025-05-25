package twitter.challenge.espenia.infra.mongodb.document;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Document(collection = "follows")
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
    @CompoundIndex(name = "follower_followed_idx", def = "{'followerId': 1, 'followedId': 1}", unique = true)
})
public class FollowDocument extends BaseDocument {

    @Id
    private String id;
    
    private String followerId;
    
    private String followedId;
    
    @CreatedDate
    private Date createdAt;
}
