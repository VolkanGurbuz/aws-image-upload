package dev.volkangurbuz.awsimageupload.bucket;

import lombok.Getter;

@Getter
public enum BucketName {
  PROFILE_IMAGE("volkangurbuz-image-upload-123");

  private final String bucketName;

  BucketName(String bucketName) {
    this.bucketName = bucketName;
  }
}
