package twitter.challenge.espenia.util;


import twitter.challenge.espenia.core.domain.User;
import twitter.challenge.espenia.core.result.UserResponse;
import twitter.challenge.espenia.core.usecase.request.UserRequest;
import twitter.challenge.espenia.core.usecase.request.UserUpdateRequest;
import twitter.challenge.espenia.infra.mongodb.document.UserDocument;
import twitter.challenge.espenia.infra.mongodb.document.TweetDocument;
import twitter.challenge.espenia.core.domain.Tweet;
import twitter.challenge.espenia.core.usecase.request.TweetRequest;
import twitter.challenge.espenia.core.result.TweetResponse;

import java.time.ZonedDateTime;
import java.util.Date;

public final class Factory {

  public static final String USERNAME = "espenia";
  public static final String USERID = "12345";
  public static final String EMAIL = "tatas323@gmail.com";
  public static final String TWEET_ID = "67890";
  public static final String TWEET_CONTENT = "This is a test tweet";

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

  public static UserRequest sampleUserCreateRequest() {
    return UserRequest.builder()
            .username(USERNAME)
            .email(EMAIL)
            .displayName("Esteban")
            .bio("This is my bio")
            .password("password123")
            .build();
  }

  public static UserUpdateRequest sampleUserUpdateRequest() {
    return UserUpdateRequest.builder()
            .displayName("Tata")
            .bio("This is my new bio")
            .build();
  }

  public static User updatedUser() {
    return User.builder()
            .id(USERID)
            .username(USERNAME)
            .email(EMAIL)
            .displayName("Tata")
            .bio("This is my new bio")
            .passwordHash("hashedpassword")
            .build();
  }

  public static UserResponse sampleUserResponse() {
    return UserResponse.builder()
            .id(USERID)
            .username(USERNAME)
            .email(EMAIL)
            .displayName("Esteban")
            .bio("This is my bio")
            .build();
  }

  public static UserResponse sampleUserUpdatedResponse() {
    return UserResponse.builder()
            .id(USERID)
            .username(USERNAME)
            .email(EMAIL)
            .displayName("Tata")
            .bio("This is my new bio")
            .build();
  }

  public static Tweet sampleTweet() {
    return Tweet.builder()
            .id(TWEET_ID)
            .userId(USERID)
            .content(TWEET_CONTENT)
            .createdAt(new Date())
            .likeCount(0)
            .build();
  }

  public static TweetDocument sampleTweetDocument() {
    return TweetDocument.builder()
            .id(TWEET_ID)
            .userId(USERID)
            .content(TWEET_CONTENT)
            .createdAt(new Date())
            .likeCount(0)
            .build();
  }

  public static TweetRequest sampleTweetCreateRequest() {
    return new TweetRequest(USERID, TWEET_CONTENT);
  }

  public static TweetResponse sampleTweetResponse() {
    return TweetResponse.builder()
            .id(TWEET_ID)
            .userId(USERID)
            .content(TWEET_CONTENT)
            .createdAt(new Date())
            .likeCount(0L)
            .build();
  }
}
