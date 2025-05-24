package twitter.challenge.espenia.infra.jpa;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class BaseJPAGateway<U, V, T extends JpaRepository<U, V>> {

  protected final T repository;

  public U save(final U entity) {
    return repository.save(entity);
  }

  public Optional<U> maybeFindById(final V id) {
    return repository.findById(id);
  }

  public void saveAll(final Iterable<U> entities) {
    repository.saveAll(entities);
  }

  public U saveAndFlush(final U entity) {
    return repository.saveAndFlush(entity);
  }

  public void saveAllAndFlush(final Iterable<U> entities) {
    repository.saveAllAndFlush(entities);
  }
}
