package com.howtodoinjava.app.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.howtodoinjava.app.model.IdentityRole;
import com.howtodoinjava.app.model.Person;

public interface IdentityRoleDAO {
  IdentityRole get(String userId);
  void create(IdentityRole identityRole);
  void update(String id, Person employee) throws JsonProcessingException;
  void delete(String id);
}