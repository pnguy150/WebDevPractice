package com.howtodoinjava.app.config;

import com.codahale.metrics.health.HealthCheck;
import jakarta.ws.rs.client.Client;

public class ApplicationHealthCheck extends HealthCheck {

  private final Client client;

  public ApplicationHealthCheck(Client client) {
    super();
    this.client = client;
  }

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}
