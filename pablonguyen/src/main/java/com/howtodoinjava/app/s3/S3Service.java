package com.howtodoinjava.app.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.howtodoinjava.app.App;
import com.howtodoinjava.app.SNS.SNS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class S3Service {
  private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

  private AmazonS3 s3 = createS3Client();

  public S3Service(AmazonS3 s3){
    this.s3 = s3;
  }

  public S3Service(){}

  public ObjectMetadata getObjectMetaData(String bucketName, String key) {
    ObjectMetadata metadata = s3.getObjectMetadata(bucketName, key);
    return metadata;
  }

  public void createObject(String bucketName, String key, String localFile) {
    s3.putObject(bucketName, key, new File(localFile));
  }

  public void deleteObject(String bucketName, String key) {
    try {
      s3.deleteObject(bucketName, key);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  public List<String> listObjects(String bucketName, String key) {
    List<String> result = new LinkedList<>();
    ListObjectsRequest request = new ListObjectsRequest().withPrefix(key).withBucketName(bucketName).withMaxKeys(100);

//    ListObjectsRequest request = new ListObjectsRequest().withPrefix(key).withDelimiter("/").withBucketName(bucketName).withMaxKeys(1);
    try {
      while(true) {
        ObjectListing objectListing = s3.listObjects(request);
        List<S3ObjectSummary> objects = objectListing.getObjectSummaries();
        List<String> keys = objects.stream().map(object -> object.getKey()).collect(Collectors.toList());
        List<String> files = keys.stream().filter(file -> !file.replaceFirst(key, "").isEmpty()).collect(Collectors.toList());
        result.addAll(files);
        if (Objects.isNull(objectListing.getNextMarker())) {
          break;
        }
        request.withMarker(objectListing.getNextMarker());
      }
      return result;
    } catch (Exception e) {
      logger.info(e.getMessage(), e);
    }
    return null;
  }

  public boolean bucketExists(String bucketName) {
    return s3.doesBucketExistV2(bucketName);
  }


  public void deleteBucket(String bucketName) {
    s3.deleteBucket(bucketName);
  }

  private static AmazonS3 createS3Client()  {
    BasicAWSCredentials creds = new BasicAWSCredentials("AKIAQYFEKP6D44OTEX2N", "mKwBYgA/MzBidPHJwOUsMOqOp3gJYPnOvNcxKLCc");
//    BasicAWSCredentials creds = new BasicAWSCredentials("", "");
    return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion("us-east-1").build();
  }

  public static void main(String[] args) throws JsonProcessingException {
    S3Service s3 = new S3Service();

    String localFile = "/Users/pablo/source/pablonguyen/README.md";

    String key = "MyLocalFile/README.md";





  }

}
