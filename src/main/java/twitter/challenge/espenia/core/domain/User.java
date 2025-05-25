package twitter.challenge.espenia.core.domain;

import lombok.*;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@ToString
@Builder
public class User {
    private final String id;
    private final String username;

    @Setter
    private String displayName;

    @Setter
    private String email;

    @Setter
    private String passwordHash;

    @Setter
    private String bio;
}
