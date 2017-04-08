package com.example.integration.gitter;

import com.example.DashboardProperties;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://developer.gitter.im/docs/welcome
 *
 * @author: clong
 * @date: 2017-03-23
 */
@Component
public class GitterClient {


  private final WebClient webClient;
  private final DashboardProperties properties;

  public GitterClient(DashboardProperties properties) {
    this.properties = properties;
    this.webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector())
        .baseUrl("https://api.gitter.im")
        .build()
        .filter(oauthToken(properties.getGitter().getToken()));
  }

  private ExchangeFilterFunction oauthToken(final String token) {
    return (request, next) -> {
      ClientRequest newRequest = ClientRequest.from(request)
          .header("Authorization", "Bearer " + token)
          .build();
      return next.exchange(newRequest);
    };
  }


  public Flux<GitterUser> getUsersInRoom(String roomId, int limit) {

    return webClient.get()
        .uri("/v1/rooms/{roomId}/users?limit={limit}", roomId, limit)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMap(response -> response.bodyToFlux(GitterUser.class));

  }


  public Mono<GitterUser> findUserInRoom(String username, String roomId) {
    return webClient.get()
        .uri("/v1/rooms/{roomId}/users?q={username}", roomId, username)
        .accept(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE )
//        .contentType(MediaType.APPLICATION_JSON)
        .exchange()
        .then(response -> response.bodyToMono(GitterUser.class));
  }

  public Flux<GitterMessage> latestChatMessages(String roomId, int limit) {
    return webClient.get()
        .uri("/v1/rooms/{roomId}/chatMessages?limit={limit}", roomId, limit)
        .accept(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE )
        .exchange()
        .flatMap(response -> response.bodyToFlux(GitterMessage.class));
  }


  public Flux<GitterMessage> streamChatMessages(String roomId) {
    return webClient.get()
        .uri("https://stream.gitter.im/v1/rooms/{roomId}/chatMessages", roomId)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMap(response -> response.bodyToFlux(GitterMessage.class));
  }


}
