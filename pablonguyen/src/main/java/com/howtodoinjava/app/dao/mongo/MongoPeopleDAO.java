package com.howtodoinjava.app.dao.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.dao.PeopleDAO;
import com.howtodoinjava.app.model.Address;
import com.howtodoinjava.app.model.Person;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.json.JsonObject;


import java.util.*;

import org.apache.commons.lang3.RandomStringUtils;

public class MongoPeopleDAO  implements PeopleDAO{
  public static final ObjectMapper mapper = new ObjectMapper();

  private final MongoCollection<Person> collection;

  // mongodb://localhost:27017
  public MongoPeopleDAO(String connectionString) {
    MongoClient client = MongoClients.create(connectionString);
    CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    MongoDatabase database = client.getDatabase("US").withCodecRegistry(pojoCodecRegistry);
    collection = database.getCollection("People", Person.class);
  }

  public List<Person> getStreetName(String streetName){
    Document document = new Document("address.streetName", streetName);
    FindIterable<Person> results = collection.find(document);
    results.batchSize(100);
    results.limit(500);

//    results.forEach(person -> persons.add(person));
    return results.into(new LinkedList<>());
  }

  @Override
  public Person get(String ssn) {
    Document document = new Document("firstName", ssn);
    FindIterable<Person> results = collection.find(document);
    return results.first();
  }

  public List<Person> getList(String ssn, int batchSize) {
//    Document document = new Document("ssn", ssn);
    Bson filter = Filters.and(Filters.eq("firstName", "SWshODvAIw"),
      Filters.eq("lastName", "SWshODvAIw"));
    FindIterable<Person> results = collection.find(filter);
//    FindIterable<Person> results = collection.find(document);

    results.batchSize(batchSize);
    results.limit(3000);

    return results.into(new LinkedList<>());
  }

  @Override
  public String insert(Person person) {
    InsertOneResult result = collection.insertOne(person);
    return result.getInsertedId().toString();
  }

  @Override
  public void update(String ssn, Person person) throws JsonProcessingException {
    JsonObject obj = new JsonObject(mapper.writeValueAsString(person));
    collection.updateOne(new Document("ssn", ssn), obj);
  }

  @Override
  public void delete(String ssn) {
    collection.deleteOne(new Document("ssn", ssn));
  }

  public static void main(String[] args) {
    MongoPeopleDAO dao = new MongoPeopleDAO("mongodb://localhost:27017");
    Address address = new Address(606, "Rheem Blvd", "Moraga", "CA", 94556L);
//    Person person = new Person("Cao", "Bar", "123-45-678", address);
//    dao.insert(person);
//    person = new Person("Claudia", "NFoo", "123-45-678", address);
//    dao.insert(person);
//    person = dao.get("Pablo");
//    person = dao.get("678-45-122");
    long startTime = System.currentTimeMillis();
    List<Person> personList = dao.getList("123-45-678", 100);
    long endTime = System.currentTimeMillis();

//    long totaltime = endTime-startTime;
//    for (int i = 0; i <10000; i++){
//      Random rand = new Random();
////      int addressNum = rand.nextInt(10000);
////      long zip_code = (long)addressNum;
//      String randomString =  RandomStringUtils.random(10, true, false);
//      Address address = new Address(i, randomString, randomString, randomString, (long)i);
//      Person person = new Person(randomString, randomString, "123-45-678", address);
//      dao.insert(person);
//    System.out.println(totaltime);
//    }
  }

}