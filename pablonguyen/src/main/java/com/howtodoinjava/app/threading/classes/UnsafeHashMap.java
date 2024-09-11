package com.howtodoinjava.app.threading.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UnsafeHashMap {

  // as document here https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
  // "Note that this implementation is not synchronized.
  // If multiple threads access a hash map concurrently, and at least one of the threads modifies the map structurally, it must be synchronized externally."


  // multiple threads can use the same instance of a UnsafeHashMap class and calls "getKey", "setKey" concurrently
  // HashMap is not thread-safe, therefore it will lead a corruption of its internal data

  // it's not just java data structure that needs to be synchronized for threading. Any object need to be proected as well.
  // In this example class the Integer age has to be protected as well.
  private Map<String, String> hashMap = new HashMap<>();

  private Integer age = new Integer(0);

  public String getKey(String key) {
    String value = hashMap.get(key);
    return value;
  }

  public void setKey(String key, String value) {
    hashMap.put(key, value);
  }

  public void addAge(int addedAge) {
    synchronized (age) {
      age = age + addedAge;
    }
  }

  public Integer getAge() {
    synchronized (age) {
      return age;
    }
  }

  public void foo() {
    int i = 0;
    Map<String, String> local = new HashMap<>();

    // safe because localHashMap reference in the thread stack, not being shared among the threads
    Map<String, String> localHashMap = new HashMap<>();
    String value = localHashMap.get("key");
    localHashMap.put("key", "value");
    localHashMap.put("key2", "value2");
  }
}