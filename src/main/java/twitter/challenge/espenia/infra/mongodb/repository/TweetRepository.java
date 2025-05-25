package twitter.challenge.espenia.infra.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import twitter.challenge.espenia.infra.mongodb.document.TweetDocument;

public interface TweetRepository extends MongoRepository<TweetDocument, String> {
}
