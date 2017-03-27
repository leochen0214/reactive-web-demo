package com.example.repository;

import com.example.domain.ReactorPerson;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: clong
 * @date: 2017-03-23
 */
@Repository
public class ReactorPersonRepository {

  public Flux<ReactorPerson> findAll() {
    return null;

  }

  public Mono<ReactorPerson> findOne(String id){
    return null;
  }
}
