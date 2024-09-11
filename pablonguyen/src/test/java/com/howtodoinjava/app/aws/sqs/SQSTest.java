package com.howtodoinjava.app.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.datastructures.Pet;
import com.howtodoinjava.app.web.exception.ServiceUnavailableException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SQSTest {

  AmazonSQS sqsMock;
  SQS sqs;

  @Before
  public void beforeEachTest() {
    sqsMock = Mockito.mock(AmazonSQS.class);
    sqs = new SQS(sqsMock);

  }

  @Test
  public void testSendMessageValidateCallTimes() {
    // explain chaining method withMessage
    // explain variable parameters

    // there are different receiveMessages with different parameter tupe. Has to use any() with a class.
    Mockito.when(sqsMock.sendMessage(Mockito.any(SendMessageRequest.class))).thenReturn(new SendMessageResult());
    sqs.sendMessage(new HelloMessage("Pablo", "dad", "travel plan"));
    Mockito.verify(sqsMock, Mockito.times(1)).sendMessage(Mockito.any(SendMessageRequest.class));
  }

  @Test
  public void testSendMessageValidateCallParameters() throws JsonProcessingException {
    // there are different receiveMessages with different parameter tupe. Has to use any() with a class.
    ArgumentCaptor<SendMessageRequest> sendMessageRequestCaptor = ArgumentCaptor.forClass(SendMessageRequest.class);
    sqs.sendMessage(new HelloMessage("Pablo", "dad", "weekend plan"));
    Mockito.verify(sqsMock).sendMessage(sendMessageRequestCaptor.capture());
    SendMessageRequest sendMessageRequest = sendMessageRequestCaptor.getValue();
    Map<String, MessageAttributeValue> msgAttribute = sendMessageRequest.getMessageAttributes();
    assertEquals(2, msgAttribute.size());
    assertEquals("Now", msgAttribute.get("Time").getStringValue());
    assertEquals("Pablo", msgAttribute.get("Sender_Name").getStringValue());
    ObjectMapper objectMapper = new ObjectMapper();
    HelloMessage helloMessage = objectMapper.readValue(sendMessageRequest.getMessageBody(), HelloMessage.class);
    assertEquals("Pablo", helloMessage.to);
    assertEquals("dad", helloMessage.from);
    assertEquals("weekend plan", helloMessage.text);
  }

  @Test(expected = ServiceUnavailableException.class)
  public void testSendMessageWithException() throws JsonProcessingException {
    // there are different receiveMessages with different parameter tupe. Has to use any() with a class.
    Mockito.when(sqsMock.sendMessage(Mockito.anyString(), Mockito.anyString())).thenThrow(new AmazonSQSException("unit test"));
    sqs.sendMessage(new HelloMessage("Pablo", "dad", "weekend plan"));
  }

  @Test
  public void testReceiveMessage() {
    // explain chaining method withMessage
    // explain variable parameters

    //use message bad as a way to purposely cause a problem and debug
//    Message messageBad = new Message().withBody("{\"from\":\"dad\",\"to\":\"Pablo\",\"message\":\"What to do during summer\"}");
    Message message = new Message().withBody("{\"from\":\"dad\",\"to\":\"Pablo\",\"text\":\"What to do during summer\"}");
    ReceiveMessageResult result = new ReceiveMessageResult().withMessages(message);

    // there are different receiveMessages with different parameter tupe. Has to use any() with a class.
    Mockito.when(sqsMock.receiveMessage(Mockito.any(ReceiveMessageRequest.class))).thenReturn(result);

    // now that the mocking is done, make call and validate
//    HelloMessage helloMessage = sqs.receiveMessage();
    MessageResult messageResult = sqs.receiveMessage();
    HelloMessage helloMessage = messageResult.helloMessage;
    assertEquals("dad", helloMessage.from);
    assertEquals("Pablo", helloMessage.to);
    assertEquals("What to do during summer", helloMessage.text);
  }

  @Test(expected = RuntimeException.class)
  public void testSendMessageProcessException() {
    // explain chaining method withMessage
    // explain variable parameters

    // there are different receiveMessages with different parameter tupe. Has to use any() with a class.
    Mockito.when(sqsMock.sendMessage(Mockito.any(SendMessageRequest.class))).thenThrow(new JsonParseException("Error", null));
    sqs.sendMessage(new HelloMessage("Pablo", "dad", "travel plan"));
    Mockito.verify(sqsMock, Mockito.times(1)).sendMessage(Mockito.any(SendMessageRequest.class));
  }


  @Test
  public void testRecievePetMessageResult() {
    Message message = new Message().withBody("{\"kind\":\"lab\",\"active\":\"true\",\"docile\":\"true\"}");
    ReceiveMessageResult result = new ReceiveMessageResult().withMessages(message);

    Mockito.when(sqsMock.receiveMessage(Mockito.any(ReceiveMessageRequest.class))).thenReturn(result);

    PetMessageResult petMessageResult= sqs.receivePetMessage();
    Pet p = petMessageResult.pet;
    assertEquals("lab", p.kind);
    assertTrue(p.active);
    assertTrue(p.docile);

    ArrayList<String> strings = new ArrayList<>(Arrays.asList("a", "b", "c"));
    ArrayList<String> strings2 = new ArrayList<>(Arrays.asList("a", "b", "c"));

    assertEquals(strings, strings2);


  }
}
