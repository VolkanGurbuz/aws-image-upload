package dev.volkangurbuz.awsimageupload.datastore;

import dev.volkangurbuz.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

  private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

  static {
    USER_PROFILES.add(
        new UserProfile(UUID.fromString("29bb09d9-1e5f-4bd9-839f-132b970f595a"), "volkan", null));
    USER_PROFILES.add(
        new UserProfile(UUID.fromString("ddab4004-6a2a-4897-9dcf-9741fed8c096"), "testuser", null));
  }

  public List<UserProfile> getUserProfiles() {
    return USER_PROFILES;
  }
}
