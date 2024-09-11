package com.howtodoinjava.app.threading;
import com.amazonaws.services.sqs.model.Message;
import com.howtodoinjava.app.aws.sqs.*;
import com.howtodoinjava.app.s3.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQSThread extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(SQSThread.class);

    private SQS aws;
    public SQSThread(SQS aws){
        this.aws = aws;
    }

    public void run(){

        while(true){
            try {
                logger.info("receiving messages");
                PetMessageResult pmr = aws.receivePetMessage();
                logger.info("after receiving messages");
                if (pmr != null) {
                    Message message = pmr.result.getMessages().get(0);
                    aws.deleteMessage(message.getReceiptHandle());
                    logger.info("delete");
                }
            } catch(Exception e) {
                logger.info("Error Occured " + e.getMessage(), e);
            }

        }
    }
}
