package twitter.challenge.espenia.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import twitter.challenge.espenia.Application;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"SCOPE_SUFFIX = integration_test"})
public class IntegrationTest {

  protected final ObjectMapper objectMapper;

  protected IntegrationTest(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  protected boolean asserEqualsButId(final Object expected, final Object actual) {
    Map actualMap = objectMapper.convertValue(actual, Map.class);
    actualMap.remove("id");
    return objectMapper.convertValue(expected, Map.class).equals(actualMap);
  }
}
