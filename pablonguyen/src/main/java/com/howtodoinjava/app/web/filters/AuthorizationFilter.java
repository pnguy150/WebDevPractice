package com.howtodoinjava.app.web.filters;

import com.howtodoinjava.app.dao.IdentityRoleDAO;
import com.howtodoinjava.app.digitalsigning.JWTToken;
import com.howtodoinjava.app.digitalsigning.TokenPayload;
import com.howtodoinjava.app.model.IdentityRole;
import com.howtodoinjava.app.web.exception.ForbiddenException;
import com.howtodoinjava.app.web.exception.UnauthorizedException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

@Priority(10)
public class AuthorizationFilter implements ContainerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
  private ResourceInfo resourceInfo;

  private final IdentityRoleDAO identityRoleDAO;

  public AuthorizationFilter(IdentityRoleDAO identityRoleDAO) {
    this.identityRoleDAO = identityRoleDAO;
  }
  @Context
  public void setResourceInfo(ResourceInfo resourceInfo) {
    this.resourceInfo = resourceInfo;
  }
  @Override
  public void filter(ContainerRequestContext context) throws IOException {
//    RequiredRole role = resourceInfo.getResourceMethod().getAnnotation(RequiredRole.class);
//    if (Objects.nonNull(role)) {
//      String requiredRoleName = role.role();
//      String authorization = context.getHeaderString("Authorization");
//      if (Objects.isNull(authorization) || authorization.isEmpty()) throw new UnauthorizedException("Authentication red");
//      if (authorization != null) {
//        String token = authorization.replace("Bearer ", "");
//        try {
//          TokenPayload payload = JWTToken.decodeToken(token);
//          IdentityRole identityRole = identityRoleDAO.get(payload.userId);
//          if (Objects.isNull(identityRole) || ! identityRole.role.equals(requiredRoleName)) {
//            logger.info(String.format("userId %s with role %s does not have the rquired role %s"), payload.userId, identityRole.role, requiredRoleName);
//            throw new ForbiddenException(String.format("user %s is not authorized", payload.userId));
//          }
//        } catch (Exception e) {
//          throw new RuntimeException(e);
//        }
//      }
//    }
    String authorization = context.getHeaderString("Authorization");
//    if (Objects.isNull(authorization) || authorization.isEmpty())
//      throw new UnauthorizedException("Authentication required");
    if (authorization != null) {
      String token = authorization.replace("Bearer ", "");
      try {
        TokenPayload payload = JWTToken.decodeToken(token);
        if (!payload.userId.equals("pablonguyen")) {
          throw new ForbiddenException(String.format("user %s is not authorized", payload.userId));
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      logger.info("filtering");
    }
  }
}
