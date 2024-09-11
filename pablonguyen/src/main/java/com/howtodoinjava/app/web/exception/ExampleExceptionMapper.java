package com.howtodoinjava.app.web.exception;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.howtodoinjava.app.web.filters.ErrorClass;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sns.model.ResourceNotFoundException;

public class ExampleExceptionMapper implements ExceptionMapper<Exception> {
  private static final Logger logger = LoggerFactory.getLogger(ExampleExceptionMapper.class);

  @Override
  public Response toResponse(Exception e) {
    logger.info(e.getLocalizedMessage(), e);
    if (e.getClass().getName().equals(UnauthorizedException.class.getName())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    } else if (e.getClass().getName().equals(ForbiddenException.class.getName())) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorClass(e.getMessage())).build();
    } else if (e.getClass().getName().equals(ServiceUnavailableException.class.getName())) {
      return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    } else if(e.getClass().getName().equals(AmazonS3Exception.class.getName())
            && e.getMessage().startsWith("The specified key does not exist")){
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorClass("The Pet ID Does Not exist")).build();
    } else if (e.getClass().getName().equals(DaoNotFoundException.class.getName())){
      return Response.status(Response.Status.NOT_FOUND).entity(new ErrorClass("Pet Id does not exist")).build();
    } else if (e.getClass().getName().equals(UnauthorizedException.class.getName())){
      return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorClass(e.getMessage())).build();
    } else if (e.getClass().getName().equals(SNSTopicInvalidException.class.getName())){
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorClass("Topic Name not found")).build();
    }
    else {
      return Response.status(400).build();
    }
  }
}
