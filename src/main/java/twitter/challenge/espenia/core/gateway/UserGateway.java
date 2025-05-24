package twitter.challenge.espenia.core.gateway;

import twitter.challenge.espenia.core.domain.User;

public interface UserGateway {

    User findById(String id);

    User findByUsername(String username);

    User findByEmail(String email);

    User create(User user);

    User update(User user);

    void delete(String id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
