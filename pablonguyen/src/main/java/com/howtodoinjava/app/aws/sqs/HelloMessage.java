package com.howtodoinjava.app.aws.sqs;

public class HelloMessage {
  public String to;
  public String from;
  public String text;

//  public String nada

  HelloMessage() {
  }

  public HelloMessage(String to, String from, String text) {
    this.to = to;
    this.from = from;
    this.text = text;
  }
}
