package com.howtodoinjava.app.authentication.dao;

import com.howtodoinjava.app.authentication.model.AuthenticationUser;

public interface IdentityDAO {
  AuthenticationUser get(String userId);
  void create(AuthenticationUser user);
  void delete(String id);
}
