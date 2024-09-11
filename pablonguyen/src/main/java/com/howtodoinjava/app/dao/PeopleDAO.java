package com.howtodoinjava.app.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.howtodoinjava.app.model.Person;

public interface PeopleDAO {
  Person get(String id);
  String insert(Person employee);
  void update(String id, Person employee) throws JsonProcessingException;
  void delete(String id);
}