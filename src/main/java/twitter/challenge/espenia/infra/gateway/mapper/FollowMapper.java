package twitter.challenge.espenia.infra.gateway.mapper;

import org.mapstruct.Mapper;
import twitter.challenge.espenia.core.domain.Follow;
import twitter.challenge.espenia.infra.mongodb.document.FollowDocument;

@Mapper(componentModel = "spring")
public interface FollowMapper extends BaseMapper<FollowDocument, Follow> {
}
