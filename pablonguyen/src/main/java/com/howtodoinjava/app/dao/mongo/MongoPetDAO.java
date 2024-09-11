package com.howtodoinjava.app.dao.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.dao.PetDAO;
import com.howtodoinjava.app.datastructures.Pet;
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

import java.util.LinkedList;

public class MongoPetDAO implements PetDAO {

    public static final ObjectMapper mapper = new ObjectMapper();

    private final MongoCollection<Pet> collection;

    public MongoPetDAO(String connectionString) {
        MongoClient client = MongoClients.create(connectionString);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase database = client.getDatabase("US").withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection("Pet", Pet.class);
    }

    @Override
    public Pet get(String id) {
        Document document = new Document("petID", id);
        FindIterable<Pet> results = collection.find(document);
        return results.first();
    }

    public LinkedList<Pet> Query(String breed, boolean active){
        Bson filter = Filters.and(Filters.eq("kind", breed),
                Filters.eq("active", active));
        FindIterable<Pet> results = collection.find(filter);
        return results.into(new LinkedList<Pet>());
    }

    @Override
    public String insert(Pet pet) {
        InsertOneResult result = collection.insertOne(pet);
        return result.getInsertedId().toString();


    }

    @Override
    public void update(String id, Pet pet) throws JsonProcessingException {
        JsonObject obj = new JsonObject(mapper.writeValueAsString(pet));
        collection.updateOne(new Document("petID", id), obj);

    }

    @Override
    public void delete(String id) {
         collection.deleteOne(new Document("petID", id));
    }
}
