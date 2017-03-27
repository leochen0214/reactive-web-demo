package com.example.integration.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author: clong
 * @date: 2017-03-24
 */
@Data
public class GithubUser {
  private Long id;
  private String login;

  @JsonProperty("avatar_url")
  private String avatarUrl;

}
