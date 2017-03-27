package com.example.domain;

import com.example.integration.github.GithubIssue;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: clong
 * @date: 2017-03-23
 */
@AllArgsConstructor
@Getter
public class ReactorIssue implements Serializable{

  private final GithubIssue issue;
  private final boolean isOnline;

}
