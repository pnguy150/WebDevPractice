package com.howtodoinjava.app.authentication.dao.mongo;

import com.howtodoinjava.app.authentication.model.AuthenticationUser;
import com.howtodoinjava.app.authentication.dao.IdentityDAO;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoIdentityDAO implements IdentityDAO {
  private final MongoCollection<AuthenticationUser> collection;

  public MongoIdentityDAO(String connectionString) {
    MongoClient client = MongoClients.create(connectionString);
    CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    MongoDatabase database = client.getDatabase("US").withCodecRegistry(pojoCodecRegistry);
    collection = database.getCollection("AuthenticationUsers", AuthenticationUser.class);
  }
  @Override
  public AuthenticationUser get(String userId) {
    Document document = new Document("userId", userId);
    FindIterable<AuthenticationUser> results = collection.find(document, AuthenticationUser.class);
    return results.first();
  }

  @Override
  public void create(AuthenticationUser user) {
    InsertOneResult result = collection.insertOne(user);
  }

  @Override
  public void delete(String id) {
  }
}
