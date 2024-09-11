package com.howtodoinjava.app.web;

import com.howtodoinjava.app.dao.PeopleDAO;
import com.howtodoinjava.app.model.Person;
import com.howtodoinjava.app.web.filters.RequiredRole;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.net.URISyntaxException;

// any request with "/peple" will be handled by the methods in this class

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleController {

  private final PeopleDAO peopleDAO;

  public PeopleController(PeopleDAO peopleDAO) {
    this.peopleDAO = peopleDAO;
  }


  // HTTP GET normally does not have a body, only either path parameters or query parameters

  // GET "/people/{ssn}" will be handled by this method. See @GET annotation
  // "/{ssn}" is a template, the value will be placed in the variable "ssn" with
  // the "@PathParam" annotation. The @PathParam("ssn"), the string in the annotation "ssn" has to match
  // with the template "/{ssn}"
  // the use if path parameter is when the value uniquely identifies an entity
  // in this case SSN is unique per person

  // an exaple is GET "/people/555667777
  @GET
  @Path("/{ssn}")
  public Response getPerson(@PathParam("ssn") String ssn) throws Exception {
    Person person = peopleDAO.get(ssn);
    if (person != null) {
      return Response.ok(person).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }


  // nested path parameters
  @GET
  @Path("/{companyId}/employees/{employeeId}")
  public Response getPerson(@PathParam("companyId") String companyId, @PathParam("employeeId") String employeeId) throws Exception {
    return  Response.ok().build();
  }

  // example of get with query parameters. Query parameters don't uniquely identify an entity
  // it may result in multiple entities. In this example the request is for people with the firstName and age.
  // since we don't use @Path annotation on this method, the default path for this method is the same as the path for
  // the class.
  @GET
  public Response getPeople(@QueryParam("firstName") String firstName, @QueryParam("age") Integer age) throws Exception {
    return  Response.ok().build();
  }


  // POST means create entity. A good implementation would make sure that there is
  // no such entity with the unique identifier existed already. In the "Person" entity
  // that means there is no existing person with the same SSN existed in backend database
  // the body a json string of the "person" object
  @POST
  public Response createPerson(Person person) throws URISyntaxException {
    return Response.ok().build();
  }


  // PUT means update entity.
  @PUT
  @Path("/{ssn}")
  @PermitAll
  public Response updatePerson(@PathParam("ssn") String ssn, Person person) {
    return Response.ok().build();
  }

  @DELETE
  @Path("/{ssn}")
  public Response updatePerson(@PathParam("ssn") String ssn) {
    return Response.ok().build();
  }
}
