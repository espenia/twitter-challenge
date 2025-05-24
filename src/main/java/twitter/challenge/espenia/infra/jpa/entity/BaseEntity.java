package twitter.challenge.espenia.infra.jpa.entity;

import static java.util.Objects.isNull;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity<T extends UUID> implements Persistable<T> {

  public abstract void setId(T id);

  @Override
  public boolean isNew() {
    return isNull(getId());
  }
}
