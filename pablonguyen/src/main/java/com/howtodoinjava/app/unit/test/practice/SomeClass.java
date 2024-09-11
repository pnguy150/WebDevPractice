package com.howtodoinjava.app.unit.test.practice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SomeClass {
  SomeInterface someInterface;
  public SomeClass(SomeInterface someInterface) {
    this.someInterface = someInterface;
  }

  public SomeEntity getEntity(String entityId) throws EntityNotFoundException {
    Map<String, String> map = new HashMap<>();
    map.put("entityId", entityId);
    List<SomeEntity> entities = someInterface.getObjects(map);
    if (entities == null) throw new EntityNotFoundException("Entity is Null");
    if (entities.isEmpty()) throw new EntityNotFoundException("entity with ID " + entityId + " not found");
    return entities.get(0);
  }




}
