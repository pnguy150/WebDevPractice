package com.howtodoinjava.app.dao.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.howtodoinjava.app.dao.IdentityRoleDAO;
import com.howtodoinjava.app.model.IdentityRole;
import com.howtodoinjava.app.model.Person;
import com.howtodoinjava.app.web.exception.ForbiddenException;
import com.howtodoinjava.app.web.exception.ServiceUnavailableException;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class MongoIdentityRoleDAO implements IdentityRoleDAO {
  private final MongoCollection<IdentityRole> collection;

  // mongodb://localhost:27017
  public MongoIdentityRoleDAO(String connectionString) {
    MongoClient client = MongoClients.create(connectionString);
    CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    MongoDatabase database = client.getDatabase("US").withCodecRegistry(pojoCodecRegistry);
    collection = database.getCollection("IdentityRole", IdentityRole.class);
  }

  @Override
  public IdentityRole get(String userId) {
    try {
      Bson bson = Filters.eq("userId", userId);
      FindIterable<IdentityRole> results = collection.find(bson);
      // null if userId is not found in collection
      return results.first();
    } catch(Exception e) {
      throw new ServiceUnavailableException(e.getMessage());
    }
  }

  @Override
  public void create(IdentityRole identityRole) {
    InsertOneResult result = collection.insertOne(identityRole);
  }

  @Override
  public void update(String id, Person employee) throws JsonProcessingException {
  }

  @Override
  public void delete(String id) {
  }
}
