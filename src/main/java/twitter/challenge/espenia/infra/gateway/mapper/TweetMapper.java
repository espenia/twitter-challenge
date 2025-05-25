package twitter.challenge.espenia.infra.gateway.mapper;

import org.mapstruct.Mapper;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.infra.mongodb.document.TweetDocument;

@Mapper(componentModel = "spring")
public interface TweetMapper extends BaseMapper<TweetDocument, Tweet> {
}
