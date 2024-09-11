package com.howtodoinjava.app.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.SNS.EmailSubscription;
import com.howtodoinjava.app.SNS.TopicRequest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.howtodoinjava.app.SNS.SNS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sns.model.Tag;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/SNS")
@Produces(MediaType.APPLICATION_JSON)

public class SNSController {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(SNSController.class);
    private final SNS sns;

    public SNSController(SNS sns){
        this.sns = sns;
    }

    @POST
    @Path("/notify/{topicName}")
//    Need to test
    public Response Notify(String message, @PathParam("topicName") String topicName) {
        sns.publishMessage(message, topicName);
        return Response.ok().build();
    }

    @POST
    public Response CreateTopic(TopicRequest topicRequest) throws JsonProcessingException {
        LOGGER.info("Create Topic Request " + mapper.writeValueAsString(topicRequest));
        sns.createTopic(topicRequest.topicName, convertToTag(topicRequest.tags));
        return Response.ok().build();
    }

    @POST
    @Path("{topicName}/subscription")
    public Response CreateSubscription(@PathParam("topicName") String topicName, EmailSubscription sub) throws JsonProcessingException {
        LOGGER.info("Creating Subscription with topic name: {} emails: {}",  topicName,  mapper.writeValueAsString(sub));
//        use thread pools to create concurrency in making subscriptions in case 1000s of emails are added
        sns.createEmailSubscription(topicName, sub.emails);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{topicName}")
    public Response Delete(@PathParam("topicName") String topicName) {
        LOGGER.info("Deleting Topic : " + topicName);
        sns.deleteTopic(topicName);
        return Response.ok().build();
    }

    private Collection<Tag> convertToTag(Map<String, String> tagMap) {
//    for converting Map of tags into AWS tag objects
//    Other SDK is way easier since it has chaining instead of stupid builders
        if(tagMap != null) {
            Stream<Tag.Builder> tagBuilders = tagMap.entrySet().stream().map(stringStringEntry ->
                    Tag.builder().key(stringStringEntry.getKey()).value(stringStringEntry.getValue())
            );
            Collection<Tag.Builder> tagBuilderColl = tagBuilders.collect(Collectors.toList());
            return tagBuilderColl.stream().map(tag -> tag.build()).collect(Collectors.toList());
        }
        return null;
    }


}
