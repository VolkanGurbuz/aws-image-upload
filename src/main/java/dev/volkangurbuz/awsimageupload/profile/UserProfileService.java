package dev.volkangurbuz.awsimageupload.profile;

import dev.volkangurbuz.awsimageupload.bucket.BucketName;
import dev.volkangurbuz.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {

  private final UserProfileDataAccessService userProfileDataAccessService;
  private final FileStore fileStore;

  @Autowired
  public UserProfileService(
      UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
    this.userProfileDataAccessService = userProfileDataAccessService;
    this.fileStore = fileStore;
  }

  List<UserProfile> getUserProfiles() {
    return userProfileDataAccessService.getUserProfiles();
  }

  void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {

    isFileEmpty(file);
    isImage(file);

    userProfileDataAccessService.getUserProfiles().stream()
        .forEach(
            userProfile -> {
              System.out.println(userProfile.getUserProfileId());
            });

    UserProfile user = getUserProfileOrThrow(userProfileId);

    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));

    String path =
        String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
    String fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
    try {
      fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
      user.setUserProfileImageLink(fileName);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private UserProfile getUserProfileOrThrow(UUID userProfileId) {
    return userProfileDataAccessService.getUserProfiles().stream()
        .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalStateException(
                    String.format("user profile %s not found ", userProfileId)));
  }

  public byte[] downloadUserProfileImage(UUID userProfileId) {
    UserProfile userProfile = getUserProfileOrThrow(userProfileId);
    String path =
        String.format(
            "%s/%s",
            BucketName.PROFILE_IMAGE.getBucketName(),
            userProfile.getUserProfileId(),
            userProfile.getUserProfileImageLink());

    return userProfile
        .getUserProfileImageLink()
        .map(key -> fileStore.download(path, key))
        .orElse(new byte[0]);
  }

  private void isImage(MultipartFile file) {
    if (!Arrays.asList(
            ContentType.IMAGE_JPEG.getMimeType(),
            ContentType.IMAGE_PNG.getMimeType(),
            ContentType.IMAGE_GIF.getMimeType())
        .contains(file.getContentType())) {
      throw new IllegalStateException("file must be image: " + file.getContentType());
    }
  }

  private void isFileEmpty(MultipartFile file) {
    if (file.isEmpty()) {
      throw new IllegalStateException("cannot upload empty file " + file.getSize());
    }
  }
}
