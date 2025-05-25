package twitter.challenge.espenia.infra.mongodb.document;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDocument implements Persistable<String> {
    public abstract String getId();

    @LastModifiedDate
    private Date updatedAt;

    @Override
    public boolean isNew() {
        return getId() == null || getId().isEmpty();
    }

}
