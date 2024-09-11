package com.howtodoinjava.app.aws.sqs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.datastructures.Pet;
import com.howtodoinjava.app.web.exception.ServiceUnavailableException;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQS {
  private static final ObjectMapper mapper = new ObjectMapper();

  //TODO: Get from config
  private String queueUrl;
  private AmazonSQS sqsClient;
  private Config appConfig;

  public SQS(Config appConfig) {
    this.appConfig = appConfig;
    this.queueUrl = this.appConfig.getString("aws.sqs.url");
    sqsClient = this.createSqsClient();
  }

  public SQS(AmazonSQS sqsClient) {
    this.sqsClient = sqsClient;
  }

  public void sendMessage(HelloMessage helloMessage) {
    // writevalue throwse exception, talk about check and unchecked exception;

    String queueMessage = null;
    try {
      helloMessage.from = "foo";
      queueMessage = mapper.writeValueAsString(helloMessage);
      Map<String, MessageAttributeValue> mavMap = new HashMap<>();
      mavMap.put("Time",  new MessageAttributeValue().withStringValue("Now").withDataType("String"));
      mavMap.put("Sender_Name", new MessageAttributeValue().withStringValue("Pablo").withDataType("String"));

      SendMessageRequest messageRequest = new SendMessageRequest().withMessageAttributes(mavMap).withMessageBody(queueMessage).withQueueUrl(this.queueUrl);
      sqsClient.sendMessage(messageRequest);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public void sendPet(Pet pet) {
    ObjectMapper mapper = new ObjectMapper();
    try{
      String json = mapper.writeValueAsString(pet);
      Map<String, MessageAttributeValue> mavMap = new HashMap<>();
      mavMap.put("Epoch_Time", new MessageAttributeValue().withStringValue(String.valueOf(System.currentTimeMillis())).withDataType("Number"));
      mavMap.put("Sender_Name", new MessageAttributeValue().withStringValue("Pablo").withDataType("String"));
      SendMessageRequest messageRequest = new SendMessageRequest().withMessageAttributes(mavMap).withMessageBody(json).withQueueUrl(this.queueUrl);
      sqsClient.sendMessage(messageRequest);
    }
    catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }

  public MessageResult receiveMessage() {
    ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(1).withWaitTimeSeconds(10).withVisibilityTimeout(30);
    ReceiveMessageResult result = sqsClient.receiveMessage(request);
    List<Message> sqsMessages = result.getMessages();
    if (sqsMessages.isEmpty()) return null;
    else {
      Message sqsMessage = sqsMessages.get(0);
      HelloMessage helloMessage = null;
      try {
        helloMessage = mapper.readValue(sqsMessage.getBody(), HelloMessage.class);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      MessageResult mr = new MessageResult(helloMessage, result);
      return mr;
    }
  }

  public PetMessageResult receivePetMessage(){
    ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(1).withWaitTimeSeconds(10).withVisibilityTimeout(5);
    ReceiveMessageResult result = sqsClient.receiveMessage(request);
    List<Message> sqsMessages = result.getMessages();
    if (sqsMessages.isEmpty()) return null;
    else{
      Message sqsMessage = sqsMessages.get(0);
      Pet pet =  null;
      try {
        pet = mapper.readValue(sqsMessage.getBody(), Pet.class);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      PetMessageResult pmr = new PetMessageResult(pet, result);
      return pmr;
    }
  }

  public void deleteMessage(String receiptHandle) {
    DeleteMessageRequest request = new DeleteMessageRequest(queueUrl, receiptHandle);
    sqsClient.deleteMessage(request);
  }

  public void changeMessageVisibility(String reciept, int timeout){
    this.sqsClient.changeMessageVisibility(this.queueUrl, reciept, timeout);
  }
  private AmazonSQS createSqsClient() {
    //TODO: NEED TO GET FROM CONFIG
    String accessKey = this.appConfig.getString("aws.sqs.access_key");
    String secretKey =  this.appConfig.getString("aws.sqs.secret_key");
    BasicAWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
    AWSStaticCredentialsProvider credsProvider = new AWSStaticCredentialsProvider(creds);
    return AmazonSQSClientBuilder.standard().withCredentials(credsProvider).withRegion(Regions.AP_EAST_1).build();
  }
}
