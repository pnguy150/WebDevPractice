package com.howtodoinjava.app.threading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleThreadPoolTask implements Runnable {
  private static final Logger LOGGER = LoggerFactory.getLogger(SampleThread.class);
  private String logString;

  SampleThreadPoolTask(String logString) {
    this.logString = logString;
  }
  @Override
  public void run() {
    LOGGER.info("I am logging " + logString);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
