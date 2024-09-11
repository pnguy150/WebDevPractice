package com.howtodoinjava.app.web;

import com.howtodoinjava.app.dao.IdentityRoleDAO;
import com.howtodoinjava.app.dao.PeopleDAO;
import com.howtodoinjava.app.model.IdentityRole;
import com.howtodoinjava.app.model.Person;
import com.howtodoinjava.app.web.filters.RequiredRole;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.net.URISyntaxException;

@Path("/identities")
@Produces(MediaType.APPLICATION_JSON)
public class IdentityRoleController {

  private final IdentityRoleDAO dao;

  public IdentityRoleController(IdentityRoleDAO dao) {
    this.dao = dao;
  }

  @POST
  public Response create(IdentityRole identityRole) {
    dao.create(identityRole);
    return Response.ok().build();
  }
}
