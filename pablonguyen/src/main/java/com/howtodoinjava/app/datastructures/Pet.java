package com.howtodoinjava.app.datastructures;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Pet {
  public String petID;
  public String kind; // dog, cat
  public boolean active;
  public boolean docile;

  public Pet(String kind, boolean active, boolean docile) {
    this.kind = kind;
    this.active = active;
    this.docile = docile;

  }

  public Pet(){}

  public static void main(String[] argv) throws JsonProcessingException {
    Pet p = new Pet("Lab", true, true);
    ObjectMapper o = new ObjectMapper();
    String s = o.writeValueAsString(p);
    System.out.println(s);
  }
}
