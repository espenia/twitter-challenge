package twitter.challenge.espenia.infra.mongodb.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Document(collection = "users")
public class UserDocument extends BaseDocument {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;

    private String displayName;
    
    @Indexed(unique = true)
    private String email;

    private String passwordHash;

    private String bio;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Version
    private long version;

    @Override
    public boolean isNew() {
        return id == null || id.isEmpty();
    }
}
