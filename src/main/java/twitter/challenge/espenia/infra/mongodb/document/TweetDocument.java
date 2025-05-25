package twitter.challenge.espenia.infra.mongodb.document;

import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Document(collection = "tweets")
@NoArgsConstructor
@AllArgsConstructor
public class TweetDocument extends BaseDocument {

    @Id
    private String id;
    
    @Indexed
    private String userId;
    
    private String content;
    
    @Indexed
    @CreatedDate
    private Date createdAt;

    @Min(0)
    private long likeCount;

}
