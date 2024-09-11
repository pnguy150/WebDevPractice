package com.howtodoinjava.app.web.filters;


import com.howtodoinjava.app.web.exception.ForbiddenException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Priority(10)
public class SecretFilter  implements ContainerRequestFilter  {
  private static final Logger logger = LoggerFactory.getLogger(SecretFilter.class);

  @Override
  public void filter(ContainerRequestContext context) throws IOException {
    // if the request doesn't have the "x-secret" header or the value "x-secret" header is not "myrandomsecret", then return
    // http status 403 and the body of the response is
    //  {"error": "need to pass x-secret"} if x-secret header is not passed
    //  {"error": "invalid secret"} value is not "myrandomsecret"
    String s = context.getHeaderString("x-secret");

    if (s == null) throw new ForbiddenException("need to pass x-secret");
    if (!s.equals("myrandomsecret")) throw new ForbiddenException("invalid secret");

    return;

  }
}
