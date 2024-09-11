package com.howtodoinjava.app.aws.sqs;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

public class MessageResult {
    public HelloMessage helloMessage;
    public ReceiveMessageResult result;

    public MessageResult(HelloMessage helloMessage, ReceiveMessageResult result){
        this.helloMessage = helloMessage;
        this.result = result;
    }


}
