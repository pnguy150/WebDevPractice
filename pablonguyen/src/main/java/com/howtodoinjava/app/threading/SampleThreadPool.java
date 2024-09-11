package com.howtodoinjava.app.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleThreadPool {
// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executors.html
  private ExecutorService executorService;

  public SampleThreadPool() {
    executorService = Executors.newFixedThreadPool(5);
  }

  public void submitTask(SampleThreadPoolTask sampleTask) {
    executorService.submit(sampleTask);
  }
}
