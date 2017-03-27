package com.example;

import com.example.web.WebConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebConfig.class)
@EnableConfigurationProperties(DashboardProperties.class)
public class ReactiveWebDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReactiveWebDemoApplication.class, args);
  }


}
