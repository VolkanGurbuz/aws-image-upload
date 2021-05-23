package dev.volkangurbuz.awsimageupload.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService {

  private final UserProfileDataAccessService userProfileDataAccessService;

  @Autowired
  public UserProfileService(UserProfileDataAccessService userProfileDataAccessService) {
    this.userProfileDataAccessService = userProfileDataAccessService;
  }

  List<UserProfile> getUserProfiles() {
    return userProfileDataAccessService.getUserProfiles();
  }

  void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
    // check if image is not empty
    // check if file is an image
    // the user exists on the database
    // grap some metadata from file if any
    // store the image in s3 and update database with s3 image link
  }
}
