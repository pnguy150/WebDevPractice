package com.howtodoinjava.app.datastructures;

public class POJOSetter {
  private String fistName;
  private String lastName;
  private int age;
  private String gender;
  public POJOSetter() {
  }

  public void setFistName(String firstName) {
    this.fistName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public static void main(String[] args) {
    POJOSetter pojo = new POJOSetter();
    pojo.setAge(10);
    pojo.setFistName("foo");
    pojo.setLastName("bar");
    pojo.setGender("male");
  }
}
