package com.howtodoinjava.app.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.datastructures.Pet;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class PetS3Service {
    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    private AmazonS3 s3;
    private Config config;

    public PetS3Service(Config appConfig){
        this.config = appConfig;
        this.s3 = createS3Client();
    }

    public ObjectMetadata getObjectMetaData(String bucketName, String petID) {
        ObjectMetadata metadata = s3.getObjectMetadata(bucketName, petID);
        return metadata;
    }
    public Pet getObject(String bucketname, String petID) throws JsonProcessingException {
        String pets3 = s3.getObjectAsString(bucketname, "pets/" + petID);
        ObjectMapper mapper = new ObjectMapper();
        Pet p = mapper.readValue(pets3, Pet.class);
        return p;
    }

    public void createObject(String bucketName, Pet pet) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(pet);
        s3.putObject(bucketName, "pets/" + pet.petID, json);
    }

    public void deleteObject(String bucketName, String key) {
        try {
            s3.deleteObject(bucketName, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public boolean bucketExists(String bucketName) {
        return s3.doesBucketExistV2(bucketName);
    }


    public void deleteBucket(String bucketName) {
        s3.deleteBucket(bucketName);
    }

    private AmazonS3 createS3Client()  {
        BasicAWSCredentials creds = new BasicAWSCredentials(config.getString("aws.s3.access_key"), this.config.getString("aws.s3.secret_key"));
//    BasicAWSCredentials creds = new BasicAWSCredentials("", "");
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion("us-east-1").build();
    }
}
