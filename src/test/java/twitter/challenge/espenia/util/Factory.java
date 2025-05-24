package twitter.challenge.espenia.util;


import twitter.challenge.espenia.core.domain.User;
import twitter.challenge.espenia.infra.mongodb.document.UserDocument;

import java.time.ZonedDateTime;
import java.util.Date;

public final class Factory {

  public static final String USERNAME = "espenia";
  public static final String USERID = "12345";
  public static final String EMAIL = "tatas323@gmail.com";

  public static User sampleUser() {
    return User.builder()
            .id(USERID)
            .username(USERNAME)
            .email(EMAIL)
            .displayName("Esteban")
            .bio("This is my bio")
            .passwordHash("hashedpassword")
            .build();
  }

  public static UserDocument sampleUserDocument() {
    return UserDocument.builder()
            .id(USERID)
            .username(USERNAME)
            .email(EMAIL)
            .displayName("Esteban")
            .bio("This is my bio")
            .passwordHash("hashedpassword")
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();
  }

  private Factory() {}

}
