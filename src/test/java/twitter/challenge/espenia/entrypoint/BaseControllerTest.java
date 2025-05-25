package twitter.challenge.espenia.entrypoint;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import twitter.challenge.espenia.infra.config.ObjectMapperConfig;
import twitter.challenge.espenia.infra.mongodb.repository.TweetRepository;
import twitter.challenge.espenia.infra.mongodb.repository.UserRepository;
import twitter.challenge.espenia.integration.ControllerTest;
import twitter.challenge.espenia.util.Factory;

public abstract class BaseControllerTest extends ControllerTest {
    protected final UserRepository userRepository;
    
    @Autowired
    protected TweetRepository tweetRepository;

    public BaseControllerTest(final UserRepository userRepository) {
        super(ObjectMapperConfig.getDefaultObjectMapper());
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUp() {
        tweetRepository.deleteAll();
        userRepository.deleteAll();
    }

    protected void basicEntitiesSetup() {
        userRepository.save(Factory.sampleUserDocument());
    }
}
