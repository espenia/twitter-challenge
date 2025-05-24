package twitter.challenge.espenia.infra.gateway;

import org.springframework.stereotype.Component;
import twitter.challenge.espenia.core.domain.User;
import twitter.challenge.espenia.core.exception.NotFoundException;
import twitter.challenge.espenia.core.gateway.UserGateway;
import twitter.challenge.espenia.infra.gateway.mapper.UserMapper;
import twitter.challenge.espenia.infra.mongodb.document.UserDocument;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;

@Component
public class UserGatewayImpl extends BaseMongoGateway<UserDocument, String, UserRepository> implements UserGateway
{
    private final UserMapper userMapper;

    public UserGatewayImpl(final UserRepository repository, final UserMapper userMapper) {
        super(repository);
        this.userMapper = userMapper;
    }

    @Override
    public boolean existsByUsername(final String username) {
        return repository.existsByUsername(username);
    }


    @Override
    public boolean existsByEmail(final String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User findByUsername(final String username) {
        return repository.findByUsername(username)
                .map(userMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
    }

    @Override
    public User findByEmail(final String email) {
        return repository.findByEmail(email)
                .map(userMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    @Override
    public User create(User user) {
        return userMapper.toDomain(repository.save(userMapper.toEntity(user)));
    }

    @Override
    public User update(User user) {
        return userMapper.toDomain(repository.save(userMapper.toEntity(user)));
    }

    @Override
    public void delete(String id) {
        maybeFindById(id).map(user -> {
            repository.delete(user);
            return user;
        });
    }

    @Override
    public User findById(String id) {
        return repository.findById(id)
                .map(userMapper::toDomain)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }
}
