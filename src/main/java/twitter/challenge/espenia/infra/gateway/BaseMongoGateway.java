package twitter.challenge.espenia.infra.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


@RequiredArgsConstructor
public class BaseMongoGateway<E, T, R extends MongoRepository<E, T>> {

    protected final R repository;

    public E save(final E entity) {
        return repository.save(entity);
    }

    public Iterable<E> saveAndFlush(final Iterable<E> entities) {
        return repository.saveAll(entities);
    }

    public void delete(final E entity) {
        repository.delete(entity);
    }

    public Optional<E> maybeFindById(final T id) {
        return repository.findById(id);
    }

}
