package com.howtodoinjava.app.threading.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SafeConcurrentHashMap {

  // as document here https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html
  // ConcurrentHashMap fully supports concurrency, we don't need to synchronize it

  // ConcurrentHashMap is thread safe, it's probably highly optimized for synchronized
  // That should minimize the calling thread waiting for the exclusive lock
  private Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

  public String getKey(String key) {
    String value = concurrentHashMap.get(key);
    return value;
  }

  public void putKey(String key, String value) {
    concurrentHashMap.put(key, value);
  }

}