package com.example.service.impl;

import com.example.DashboardProperties;
import com.example.integration.github.GithubIssue;
import com.example.integration.gitter.GitterMessage;
import com.example.domain.ReactorIssue;
import com.example.integration.github.GithubClient;
import com.example.integration.gitter.GitterClient;
import com.example.integration.gitter.GitterUser;
import com.example.service.DashboardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: clong
 * @date: 2017-03-23
 */
@Service
public class DefaultDashboardService implements DashboardService {
  private static final Logger logger = LoggerFactory.getLogger(DefaultDashboardService.class);

  private final GithubClient githubClient;
  private final GitterClient gitterClient;
  private final DashboardProperties properties;

  public DefaultDashboardService(GithubClient githubClient,
                                 GitterClient gitterClient,
                                 DashboardProperties properties) {
    this.githubClient = githubClient;
    this.gitterClient = gitterClient;
    this.properties = properties;
  }


  @Override
  public Flux<ReactorIssue> findReactorIssues() {

//     return githubClient.findOpenIssues("reactor", "reactor-core")
//        .map(issue-> new ReactorIssue(issue, false));

    String gitterRoomId = properties.getReactor().getGitterRoomId();

    Flux<GithubIssue> issuesFlux = githubClient.findOpenIssues("reactor", "reactor-core");
    Mono<List<GitterUser>> usersMono = gitterClient.getUsersInRoom(gitterRoomId, 300)
        .collectList();

    return usersMono.flatMap(userList -> issuesFlux.map(issue -> {
      logger.info("issue.getUser()={}", issue.getUser());
      String userLogin = issue.getUser().getLogin();
      boolean isOnline = userList.stream()
          .anyMatch(user -> user.getUsername().equals(userLogin));
      return new ReactorIssue(issue, isOnline);
    }));

  }

  @Override
  public Flux<GitterMessage> getLatestChatMessages(int limit) {
    return gitterClient.latestChatMessages(properties.getReactor().getGitterRoomId(), limit);
  }

  @Override
  public Flux<GitterMessage> streamChatMessages() {
    return gitterClient.streamChatMessages(properties.getReactor().getGitterRoomId());
  }
}
