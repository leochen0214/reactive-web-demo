package com.example.integration.github;

import com.example.DashboardProperties;

import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

/**
 * github developer:  https://developer.github.com/v3/
 *
 * @author: clong
 * @date: 2017-03-23
 */
@Component
public class GithubClient {

  private static final MediaType VND_GITHUB_V3 =
      MediaType.valueOf("application/vnd.github.v3+json");

  private final WebClient webClient;
  private final DashboardProperties properties;

  public GithubClient(DashboardProperties properties) {
    this.properties = properties;
    this.webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector())
        .build()
        .filter(basicAuthentication())
        .filter(userAgent());
  }

  private ExchangeFilterFunction basicAuthentication() {
    String username = properties.getGithub().getUsername();
    String token = properties.getGithub().getToken();

    return ExchangeFilterFunctions.basicAuthentication(username, token);
  }

  private ExchangeFilterFunction userAgent() {
    return (request, next) -> {
      ClientRequest newRequest = ClientRequest.from(request)
          .header("User-Agent", "spring")
          .build();
      return next.exchange(newRequest);
    };
  }

  public Flux<GithubIssue> findOpenIssues(String owner, String repo) {
    return webClient.get()
        .uri("https://api.github.com/repos/{owner}/{repo}/issues?state=open", owner, repo)
        .accept(VND_GITHUB_V3)
        .exchange()
        .flatMap(response -> response.bodyToFlux(GithubIssue.class));
  }



}
