package com.howtodoinjava.app.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.app.dao.PeopleDAO;
import com.howtodoinjava.app.dao.mongo.MongoPetDAO;
import com.howtodoinjava.app.model.Person;
import com.howtodoinjava.app.s3.PetS3Service;
import com.howtodoinjava.app.s3.S3Service;
import com.howtodoinjava.app.web.exception.DaoNotFoundException;
import com.howtodoinjava.app.web.filters.RequiredRole;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.UUID;

import com.howtodoinjava.app.datastructures.*;
import com.howtodoinjava.app.aws.sqs.*;
@Path("/pet")
@Produces(MediaType.APPLICATION_JSON)
public class PetController {
    private SQS aws;
    private PetS3Service s3;
    private MongoPetDAO dao;

    public PetController(SQS aws, MongoPetDAO petdao, PetS3Service s3){
        this.aws = aws;
        this.dao = petdao;
        this.s3 = s3;

    }

    @GET
    public LinkedList<Pet> getPetQuery(@QueryParam("kind") String breed, @QueryParam("active") boolean active){
        LinkedList<Pet> p =  dao.Query(breed, active);
        return p;
    }

    @GET
    @Path("/{petID}")
    public Pet getPet(@PathParam("petID") String petID){
//        try{
//            Pet pet = dao.get(petID);
//            if (pet == null){
//                Pet p =  s3.getObject("petbucketpablo", petID);
//                return p;
//            }
//            return pet;
//        }
//        catch (Exception e){
//            Pet p =  s3.getObject("petbucketpablo", petID);
//            return p;
//        }
       Pet pet =  dao.get(petID);
       if (pet == null){
           throw new DaoNotFoundException("Invalid Pet ID");
       }
       return pet;
    }

    @POST
    public Response createPet(Pet pet) throws URISyntaxException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(pet);
        pet.petID = UUID.randomUUID().toString();
        aws.sendPet(pet);
//        dao.insert(pet);
//        s3.createObject("petbucketpablo", pet);
        return Response.ok().build();
    }


    @PUT
    @Path("/{kind}")
    @PermitAll
    public Response updatePerson(@PathParam("kind") String kind, Person person) {

        System.out.println(kind);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{kind}")
    public Response updatePerson(@PathParam("kind") String kind) {
        System.out.println(kind);
        return Response.ok().build();
    }
}

