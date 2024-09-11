package com.howtodoinjava.app.aws.sqs;

import com.howtodoinjava.app.App;
import com.howtodoinjava.app.dao.mongo.MongoPetDAO;
import com.howtodoinjava.app.datastructures.Pet;
import com.howtodoinjava.app.s3.PetS3Service;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QueueThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueThread.class);
    private final SQS sqs;
    private PetS3Service s3;
    private MongoPetDAO dao;

    public QueueThread(SQS sqs, MongoPetDAO dao, PetS3Service s3) {
        this.dao = dao;
        this.s3 = s3;
        this.sqs = sqs;
    }

    @SneakyThrows
    public void run() {
//    sqs.sendMessage(sendMsg);
        while (true) {
            try {
                PetMessageResult pmr = sqs.receivePetMessage();
                if (pmr != null) {
                    Pet p = pmr.pet;
                    dao.insert(p);
                    s3.createObject("petbucketpablo", p);

                    sqs.deleteMessage(pmr.result.getMessages().get(0).getReceiptHandle());


                }

            } catch (Exception e) {
                LOGGER.warn("Exception error " + e.getMessage(), e);
            }
        }
    }
}
