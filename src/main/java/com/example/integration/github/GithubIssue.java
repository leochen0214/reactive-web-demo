package com.example.integration.github;

import lombok.Data;

/**
 * @author: clong
 * @date: 2017-03-23
 */
@Data
public class GithubIssue {
  private Long id;
  private Long number;
  private String title;
  private String body;
  private String state;
  private boolean locked;

  private GithubUser user;

}
