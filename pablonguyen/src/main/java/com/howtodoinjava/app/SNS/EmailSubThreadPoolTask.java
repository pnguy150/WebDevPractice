package com.howtodoinjava.app.SNS;

import com.howtodoinjava.app.SNS.SNS;
import com.howtodoinjava.app.threading.SampleThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;

public class EmailSubThreadPoolTask implements Runnable{
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleThread.class);
    private SnsClient sns;
    private String arn;
    private String email;

    public EmailSubThreadPoolTask(SnsClient sns, String arn, String email) {
        this.sns = sns;
        this.arn = arn;
        this.email = email;
    }
    @Override
    public void run() {
        try {
            LOGGER.info("Inside Thread task: making Subscription");
            SubscribeRequest sr = SubscribeRequest.builder().protocol("email").endpoint(this.email).topicArn(this.arn).build();
            sns.subscribe(sr);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }


}
