package com.example.integration.gitter;

import lombok.Data;

/**
 * @author: clong
 * @date: 2017-03-23
 */
@Data
public class GitterMessage {
  private String id;
  private String text;
  private GitterUser fromUser;

}
