package com.howtodoinjava.app;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.howtodoinjava.app.authentication.IdentityController;
import com.howtodoinjava.app.aws.sqs.QueueThread;
import com.howtodoinjava.app.aws.sqs.SQS;
import com.howtodoinjava.app.config.ApplicationConfiguration;
import com.howtodoinjava.app.config.ApplicationHealthCheck;
import com.howtodoinjava.app.authentication.dao.mongo.MongoIdentityDAO;
import com.howtodoinjava.app.dao.IdentityRoleDAO;
import com.howtodoinjava.app.dao.mongo.MongoIdentityRoleDAO;
import com.howtodoinjava.app.dao.mongo.MongoPeopleDAO;
import com.howtodoinjava.app.dao.mongo.MongoPetDAO;
import com.howtodoinjava.app.s3.PetS3Service;
import com.howtodoinjava.app.threading.SQSThread;
import com.howtodoinjava.app.web.*;
import com.howtodoinjava.app.authentication.AuthenticationController;
import com.howtodoinjava.app.web.exception.ExampleExceptionMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.ws.rs.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.howtodoinjava.app.SNS.*;


public class App extends Application<ApplicationConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
  private static final String mongoConn = "mongodb://localhost:27017";

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> b) {
  }

  @Override
  public void run(ApplicationConfiguration c, Environment e) throws Exception {
    LOGGER.info("Registering Jersey Client");
    final Client client = new JerseyClientBuilder(e)
        .using(c.getJerseyClientConfiguration())
        .build(getName());
    Config appConfig = ConfigFactory.load();
    SNS sns = new SNS(appConfig);


    IdentityRoleDAO identityRoleDAO = new MongoIdentityRoleDAO(mongoConn);

    SQS sqs = new SQS(appConfig);
    PetS3Service s3 = new PetS3Service(appConfig);
    MongoPetDAO petDAO = new MongoPetDAO("mongodb://localhost:27017");

//    e.jersey().register(new AuthorizationFilter(identityRoleDAO));
//    e.jersey().register(new SecretFilter());
//    e.jersey().register(new AuthorizationFilter(identityRoleDAO));
    e.jersey().register(new ExampleExceptionMapper());
    e.jersey().register(new APIController(client));
    LOGGER.info("Registering REST resources");

    MongoIdentityDAO authenticationUserDAO = new MongoIdentityDAO(mongoConn);
    e.jersey().register(new IdentityController(authenticationUserDAO));

    e.jersey().register(new IdentityRoleController(identityRoleDAO));
    e.jersey().register(new AuthenticationController(authenticationUserDAO));
    e.jersey().register(new PeopleController(new MongoPeopleDAO(mongoConn)));
    e.jersey().register(new PetController(sqs, petDAO, s3));
    e.jersey().register(new SNSController(sns));
    SQSThread t = new SQSThread(sqs);

    t.start();

    LOGGER.info("Registering Application Health Check");
    e.healthChecks().register("application", new ApplicationHealthCheck(client));
    try {
//      S3Service s3Service = new S3Service();
//      boolean b = s3Service.bucketExists("invalid bucket name");
//     s3Service.createObject("data-mgmt-va7-stg-aep1-va6-stage-wfskiqxm-caotest-bucket", "newjar/acs.txt", "/Users/caonguye/source/notes/acs.txt");
//      s3Service.createObject("caotestbucketxxx", "newjar/acs.txt", "/Users/caonguye/source/notes/acs.txt");

//    s3Service.deleteObject("caotestbucketxxx", "jarxxx/acs.txt");

//    List<String> keys = s3Service.listObjects("data-mgmt-va7-stg-aep1-va6-stage-wfskiqxm-library-bucket", "jar");
//    List<String> keys = s3Service.listObjects("caotestbucket", "jar/acs.txt");
//    s3Service.getObjectMetaData("data-mgmt-va7-stg-aep1-va6-stage-wfskiqxm-library-bucket", "jar/test.jar");
//    boolean bucketExists = s3Service.bucketExists("data-mgmt-va7-stg-aep1-va6-stage-wfskiqxm-caotest-bucket");

//    s3Service.deleteBucket("data-mgmt-va7-stg-aep1-va6-stage-wfskiqxm-caotest-bucket");

//    s3Service.getObjectMetaData("data-mgmt-va7-stg-aep1-va6-stage-wfskiqxm-caotest-bucket", "jar/acs.txt");


//      SampleThread.startSampleThread();

      QueueThread queueThread = new QueueThread(sqs, petDAO, s3);
      queueThread.start();

      LOGGER.info("Registering Apache HttpClient");
    } catch (AmazonS3Exception ex) {
      System.out.println(ex.getErrorMessage());
    }



    /*LOGGER.info("Registering Apache HttpClient");
    final HttpClient httpClient = new HttpClientBuilder(e)
        .using(c.getHttpClientConfiguration())
        .build(getName());
    e.jersey().register(new APIController(httpClient));*/

    //****** Dropwizard security - custom classes ***********/
//    e.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>();
//        .setAuthenticator(new AppBasicAuthenticator())
//        .setAuthorizer(new AppAuthorizer())
//        .setRealm("BASIC-AUTH-REALM")
//        .buildAuthFilter()));
//    e.jersey().register(RolesAllowedDynamicFeature.class);
//    e.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
  }

  public static void main(String[] args) throws Exception {
    new App().run(args);
  }
}


// cao account
// queue
// https://sqs.us-east-1.amazonaws.com/051884228487/QueueOne
// arn: arn:aws:sqs:us-east-1:051884228487:QueueOne
