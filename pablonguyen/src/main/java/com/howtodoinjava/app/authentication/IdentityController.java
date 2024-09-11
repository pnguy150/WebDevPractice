package com.howtodoinjava.app.authentication;

import com.howtodoinjava.app.authentication.model.AuthenticationUser;
import com.howtodoinjava.app.authentication.model.AuthenticationUserRequest;
import com.howtodoinjava.app.authentication.dao.IdentityDAO;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.net.URISyntaxException;

@Path("/authentication/users")
public class IdentityController {
  private final IdentityDAO dao;
  public IdentityController(IdentityDAO dao) {
    this.dao = dao;
  }

  @POST
  public Response create(AuthenticationUserRequest req) throws URISyntaxException {
    AuthenticationUser user = new AuthenticationUser(req.userId, PasswordHash.hash(req.password));
    dao.create(user);
    return Response.ok().build();
  }
}
