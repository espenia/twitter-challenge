package twitter.challenge.espenia.infra.gateway.mapper;

import org.mapstruct.Mapper;
import twitter.challenge.espenia.core.domain.TimelineCache;
import twitter.challenge.espenia.infra.mongodb.document.TimelineCacheDocument;

@Mapper(componentModel = "spring")
public interface TimelineCacheMapper extends BaseMapper<TimelineCacheDocument, TimelineCache> {
}
