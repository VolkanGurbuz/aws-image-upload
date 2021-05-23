package dev.volkangurbuz.awsimageupload.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class UserProfile {
  private final UUID userProfileId;
  private final String username;
  private String userProfileImageLink; // s3 key will be used

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserProfile that = (UserProfile) o;
    return Objects.equals(userProfileId, that.userProfileId)
        && Objects.equals(username, that.username)
        && Objects.equals(userProfileImageLink, that.userProfileImageLink);
  }

  public Optional<String> getUserProfileImageLink() {
    return Optional.ofNullable(userProfileImageLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userProfileId, username, userProfileImageLink);
  }
}
