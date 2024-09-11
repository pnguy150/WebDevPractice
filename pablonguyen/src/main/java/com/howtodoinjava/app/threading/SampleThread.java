package com.howtodoinjava.app.threading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleThread extends Thread {
  private static final Logger LOGGER = LoggerFactory.getLogger(SampleThread.class);

  private SampleThreadPool threadPool;

  public SampleThread() {
    threadPool = new SampleThreadPool();
  }
  public void run() {
    while(true) {
      LOGGER.info("sample thread running");
      int i = 0;
      try {
        threadPool.submitTask(new SampleThreadPoolTask("log " + i++));
        threadPool.submitTask(new SampleThreadPoolTask("log " + i++));
        threadPool.submitTask(new SampleThreadPoolTask("log " + i++));
        threadPool.submitTask(new SampleThreadPoolTask("log " + i++));
        threadPool.submitTask(new SampleThreadPoolTask("log " + i++));
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public static void startSampleThread() {
    SampleThread sampleThread = new SampleThread();
    sampleThread.start();
  }
}
