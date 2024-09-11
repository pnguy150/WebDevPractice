package com.howtodoinjava.app.SNS;

import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SNS {
    private static final Logger LOGGER = LoggerFactory.getLogger(SNS.class);
    private final SnsClient snsClient;
    private final String arnHeader;
    private final ExecutorService executor;

    public SNS(Config config){
//        create executor, add each task to executor service
//
        this.executor = Executors.newFixedThreadPool(50);
        this.arnHeader = config.getString("aws.sns.arn");
        AwsBasicCredentials creds = AwsBasicCredentials.create(config.getString("aws.sns.access_key"),config.getString("aws.sns.secret_key"));
        this.snsClient = SnsClient.builder().credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(Region.US_EAST_1)
                .build();
    }

    public void publishMessage(String message, String topicName) {
        LOGGER.info("publishing topic {} message {}", topicName, message);
        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(this.arnHeader + topicName)
                .build();
        PublishResponse result = this.snsClient.publish(request);
        LOGGER.info("Publish message: Topic Name: {}, Message: {}Message Response id: {}, sequence number: {}" ,
                topicName, message, result.messageId(), result.sequenceNumber());
    }

    public void createEmailSubscription(String topicName, List<String> emails){

        for (String email : emails) {
            LOGGER.info("Creating Subscription with email: {}", email);
//            submit a task to the executor, 50 threads in the pool
            EmailSubThreadPoolTask task = new EmailSubThreadPoolTask(this.snsClient, this.arnHeader + topicName, email);
            executor.submit(task);
        }
    }

    public void createTopic(String topicName, Collection<Tag> tags){
        LOGGER.info("Creating Topic with name: {}", topicName);
        CreateTopicRequest topic = CreateTopicRequest.builder().name(topicName).tags(tags).build();
        snsClient.createTopic(topic);
    }

    public void deleteTopic(String topicName){
        LOGGER.info("Deleting Topic with Name: {}");
        DeleteTopicRequest topic = DeleteTopicRequest.builder().topicArn(this.arnHeader +topicName).build();
        snsClient.deleteTopic(topic);
    }
}
