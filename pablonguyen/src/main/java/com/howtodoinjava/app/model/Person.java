package com.howtodoinjava.app.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
//@NoArgsConstructor

//@Data

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
  public String firstName;
  public String lastName;
  public Address address;
  public String ssn;

  public Person() {
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public void setAddress(Address address){
    this.address = address;
  }
  public void setSsn(String ssn){
    this.ssn = ssn;
  }

  public Person(String firstName, String lastName, String ssn, Address address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.ssn = ssn;
  }

  public static void main(String[] args) throws JsonProcessingException {
    String s = "{\"firstName\":\"Pablo\",\"lastName\":\"Nguyen\",\"ssn\":\"123-45-678\",\"address\":{\"streetNumber\":606,\"streetName\":\"Rheem Blvd\",\"apartment\":null,\"city\":\"Moraga\",\"state\":null,\"zipCode\":94556,\"email\":null}}";
    ObjectMapper mapper = new ObjectMapper();

//    Address address = new Address(606, "Rheem Blvd", "Moraga", "CA", 94556L);

//    Person pablo = new Person("Pablo", "Nguyen", "123-45-678", address );

//    String s = mapper.writeValueAsString(pablo);


      Person pablo = mapper.readValue(s, Person.class);
  }
}
