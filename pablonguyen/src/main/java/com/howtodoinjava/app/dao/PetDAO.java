package com.howtodoinjava.app.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.howtodoinjava.app.datastructures.Pet;

public interface PetDAO {

    Pet get(String id) throws Exception;
    String insert(Pet pet);
    void update(String id, Pet pet) throws JsonProcessingException;
    void delete(String id);
}
