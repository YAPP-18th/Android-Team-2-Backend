package com.sns.zuzuclub.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.ImageUploadErrorCodeType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Uploader {

  private AmazonS3 s3Client;

  @Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;

  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${cloud.aws.region.static}")
  private String region;

  @PostConstruct
  public void setS3Client() {
    AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

    s3Client = AmazonS3ClientBuilder.standard()
                                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                    .withRegion(this.region)
                                    .build();
  }

  @Transactional
  public String upload(Long userId, MultipartFile file){

    // File 객체 생성
    File uploadFile = convertToFileObject(file);
    String uploadImageUrl = putS3(userId, uploadFile);
    removeNewFile(uploadFile);
    return uploadImageUrl;
//    ObjectMetadata objectMetadata = new ObjectMetadata();
//    objectMetadata.setContentType(file.getContentType());
//
//    try (InputStream inputStream = file.getInputStream()) {
//      PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uniqueFileName, inputStream,
//          objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
//      s3Client.putObject(putObjectRequest);
//    } catch (IOException e) {
//      throw new CustomException(ImageUploadErrorCodeType.FILE_IO_ERROR);
//  }
//    return s3Client.getUrl(bucket, uniqueFileName).toString();
  }

  private void removeNewFile(File uploadFile) {
    if (uploadFile.delete()) {
//      log.info("파일이 삭제되었습니다.");
    } else {
//      log.info("파일이 삭제되지 못했습니다.");
    }
  }

  private String putS3(Long userId, File uploadFile) {
    String uniqueFileName =  createUniqueFileName(userId, uploadFile.getName());
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uniqueFileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead);
    s3Client.putObject(putObjectRequest);
    return s3Client.getUrl(bucket, uniqueFileName).toString();
  }

  private File convertToFileObject(MultipartFile file) {

    File convertFile = new File(file.getOriginalFilename());

    try  {
      if (!convertFile.createNewFile()){
        throw new IllegalArgumentException();
      }
      FileOutputStream fos = new FileOutputStream(convertFile);
      fos.write(file.getBytes());
      fos.close();
    } catch (IOException e) {
      throw new CustomException(ImageUploadErrorCodeType.FILE_IO_ERROR);
    } catch (IllegalArgumentException e){
      throw new CustomException(ImageUploadErrorCodeType.ALREADY_EXIST_FILE);
    }

    return convertFile;
  }

  private String createUniqueFileName(Long userId, String originalFilename) {
    String folder_delimiter = "/";
    String file_delimiter = "-";
    return userId.toString() + folder_delimiter + LocalDateTime.now() + file_delimiter + originalFilename;
  }
}
