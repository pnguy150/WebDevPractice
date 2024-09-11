package com.howtodoinjava.app.model;

public class Address {

  public Address() {
  }

  public Address(long streetNumber, String streetName, String city, String state, Long zipCode) {
    this.streetNumber = streetNumber;
    this.streetName = streetName;
    this.city = city;
    this.zipCode = zipCode;
  }

  public Address withEmail(String email) {
    this.email = email;
    return this;
  }

  public Address withApartment(String apartment) {
    this.apartment = apartment;
    return this;
  }

  public long streetNumber;
  public String streetName;
  public String apartment;
  public String city;
  public String state;
  public Long zipCode;
  public String email;
}