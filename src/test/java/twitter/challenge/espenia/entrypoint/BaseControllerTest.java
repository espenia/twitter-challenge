package twitter.challenge.espenia.entrypoint;


import org.junit.jupiter.api.BeforeEach;
import twitter.challenge.espenia.infra.config.ObjectMapperConfig;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;
import twitter.challenge.espenia.integration.ControllerTest;
import twitter.challenge.espenia.util.Factory;

public abstract class BaseControllerTest extends ControllerTest {
    protected final UserRepository userRepository;

    public BaseControllerTest(final UserRepository userRepository) {
        super(ObjectMapperConfig.getDefaultObjectMapper());
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    protected void basicEntitiesSetup() {
        userRepository.save(Factory.sampleUserDocument());
    }
}
