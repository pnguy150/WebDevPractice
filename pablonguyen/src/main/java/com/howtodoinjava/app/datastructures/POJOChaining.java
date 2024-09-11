package com.howtodoinjava.app.datastructures;

import org.hibernate.validator.metadata.BeanMetaDataClassNormalizer;

import java.net.URISyntaxException;

public class POJOChaining {
  private String fistName;
  private String lastName;
  private int age;
  private String gender;
  public POJOChaining(String firstName, String lastName) {
    this.fistName = firstName;
    this.lastName = lastName;
  }

  public POJOChaining withAge(int age) {
    this.age =age;
    return this;
  }

  public POJOChaining withGender(String gender) {
    this.gender = gender;
    return this;
  }

  public void throwCheckedException() throws URISyntaxException {
    throw new URISyntaxException("foo", "bar");
  }

  public void throwUnchecddException() {
    throw new NullPointerException();
  }

  public void caller1() {
    try {
      throwUnchecddException();
    } catch(NullPointerException npe) {

    }
  }

  public void caller2() {
    caller1();
  }
  public static void main(String[] args) {
    POJOChaining pojo = new POJOChaining("foo", "bar").withAge(10).withGender("male");
  }
}
