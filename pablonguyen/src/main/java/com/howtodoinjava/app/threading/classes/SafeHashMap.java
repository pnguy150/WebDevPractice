package com.howtodoinjava.app.threading.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SafeHashMap {



  // as document here https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
  // "Note that this implementation is not synchronized.
  // If multiple threads access a hash map concurrently, and at least one of the threads modifies the map structurally, it must be synchronized externally."
  private Map<String, String> hashMap = new HashMap<>();

  // here to protect HashMap from concurrency we need to "synchronized" it, basically each thread
  // has to obtain an exclusive lock on the hashMap before it can do any operation on the hashMap

  // the problem with this implementation is that hashMap becomes a single point of entry.
  // each thread must wait for the lock to be available before it can do any operation on the hashMap.
  // this is un-scalable.

  public String getKey(String key) {
    // attempt to get a lock on the hashMap
    synchronized (hashMap) {
      // here this calling thread has exclusive lock on the hashMap
      String value = hashMap.get(key);
      return value;
    }
    // once the synchronized block exits, the lock on the hashMap is released,
  }

  public void putKey(String key, String value) {
    synchronized (hashMap) {
      hashMap.put(key, value);
    }
  }

  public void doMongoDBLookuo(String key) {
    synchronized (hashMap) {
      String value = hashMap.get(key);

      // here for example we make a call to MongoDB that may take 100 ms
      // during that 100 ms all other threads must wait for the lock on hashMap to be released.
      // mongoDB.get(value)
    }
  }
}