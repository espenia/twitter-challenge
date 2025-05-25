package twitter.challenge.espenia.infra.gateway.mapper;

import org.mapstruct.Mapper;
import twitter.challenge.espenia.core.domain.User;
import twitter.challenge.espenia.infra.mongodb.document.UserDocument;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDocument, User> {
}
