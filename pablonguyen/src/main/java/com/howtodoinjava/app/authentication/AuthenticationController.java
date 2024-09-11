package com.howtodoinjava.app.authentication;

import com.howtodoinjava.app.authentication.model.AuthenticationUser;
import com.howtodoinjava.app.authentication.model.AuthenticationUserResponse;
import com.howtodoinjava.app.authentication.dao.IdentityDAO;
import com.howtodoinjava.app.authentication.model.UserCredentials;
import com.howtodoinjava.app.digitalsigning.JWTToken;
import com.howtodoinjava.app.web.exception.UnauthorizedException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.net.URISyntaxException;

@Path("/authenticate")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationController {
  private final IdentityDAO dao;
  public AuthenticationController(IdentityDAO dao) {
    this.dao = dao;
  }
  @POST
  public Response authenticate(UserCredentials credentials) throws URISyntaxException {
    AuthenticationUser user = dao.get(credentials.userId);
    String hashedPassword = PasswordHash.hash(credentials.password);
    if (hashedPassword.equals(user.hashedPassword)) {
      String token = JWTToken.generateToken(user.userId);
      AuthenticationUserResponse response = new AuthenticationUserResponse(token);
      return Response.status(Status.OK).entity(response).build();
    } else {
      throw new UnauthorizedException("Incorrect Password");
    }
  }
}
