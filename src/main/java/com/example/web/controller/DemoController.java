package com.example.web.controller;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.nio.charset.StandardCharsets;

import reactor.core.publisher.Mono;

/**
 * @author: clong
 * @date: 2017-03-23
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

  private final DataBufferFactory factory = new DefaultDataBufferFactory();

  @GetMapping("/test")
  public String test() {
    return "hello pay";
  }


  @GetMapping("/hello")
  public Mono<String> hello (@RequestParam String name){
    return Mono.just("hello " + name);
  }

  @GetMapping("/exchange")
  public Mono<Void> exchange(ServerWebExchange exchange){
    ServerHttpResponse response = exchange.getResponse();

    response.setStatusCode(HttpStatus.OK);
    response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
    DataBuffer buffer = factory.allocateBuffer().write("exchange".getBytes(StandardCharsets.UTF_8));

    return response.writeWith(Mono.just(buffer));
  }


  @GetMapping("/wait")
  public Mono<String> waiting(){
    return Mono.never();
  }


  @GetMapping("/error")
  public Mono<String> error(){
    return Mono.error(new IllegalArgumentException("boom"));
  }
}
