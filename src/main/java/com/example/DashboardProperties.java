package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author: clong
 * @date: 2017-03-23
 */
@ConfigurationProperties("dashboard")
@Data
public class DashboardProperties {

  private final Reactor reactor = new Reactor();
  private final Github github = new Github();
  private final Gitter gitter = new Gitter();


  @Data
  public static class Reactor {

    private String gitterRoomId = "5534f75e15522ed4b3df41a7";

  }

  @Data
  public static class Github {

    private String username;
    private String token;

  }

  @Data
  public static class Gitter {

    private String token;

  }

}
