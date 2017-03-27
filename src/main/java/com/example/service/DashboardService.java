package com.example.service;

import com.example.integration.gitter.GitterMessage;
import com.example.domain.ReactorIssue;

import reactor.core.publisher.Flux;

/**
 * @author: clong
 * @date: 2017-03-23
 */
public interface DashboardService {

  Flux<ReactorIssue> findReactorIssues();

  Flux<GitterMessage> getLatestChatMessages(int limit);

  Flux<GitterMessage> streamChatMessages();

}
