package dev.volkangurbuz.awsimageupload.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {
  private final AmazonS3 s3;

  @Autowired
  public FileStore(AmazonS3 s3) {
    this.s3 = s3;
  }

  public void save(
      String path,
      String fileName,
      Optional<Map<String, String>> optionalMetadata,
      InputStream inputStream) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    optionalMetadata.ifPresent(
        stringStringMap -> {
          if (!stringStringMap.isEmpty()) {
            stringStringMap.forEach(objectMetadata::addUserMetadata);
          }
        });
    try {

      s3.putObject(path, fileName, inputStream, objectMetadata);
    } catch (AmazonServiceException e) {
      throw new IllegalStateException("Failed to store file to s3", e);
    }
  }

  public byte[] download(String path, String key) {
    try {
      return IOUtils.toByteArray(s3.getObject(path, key).getObjectContent());
    } catch (AmazonServiceException | IOException e) {
      throw new IllegalStateException("Failed to download file to s3", e);
    }
  }
}
