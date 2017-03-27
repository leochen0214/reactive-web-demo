package com.example.web.controller;

import com.example.integration.gitter.GitterMessage;
import com.example.domain.ReactorIssue;
import com.example.domain.ReactorPerson;
import com.example.exception.PersonNotFoundException;
import com.example.repository.ReactorPersonRepository;
import com.example.service.DashboardService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: clong
 * @date: 2017-03-23
 */
@Controller
public class DashboradController {

  private final ReactorPersonRepository reactorPersonRepository;
  private final DashboardService dashboardService;

  public DashboradController(ReactorPersonRepository reactorPersonRepository,
                             DashboardService dashboardService) {
    this.reactorPersonRepository = reactorPersonRepository;
    this.dashboardService = dashboardService;
  }



  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/reactor/person")
  @ResponseBody
  public Flux<ReactorPerson> findAll(){
    return reactorPersonRepository.findAll() ;
  }

  @GetMapping("/reactor/person/{id}")
  @ResponseBody
  public Mono<ReactorPerson> findOne(@PathVariable String id){
    return reactorPersonRepository.findOne(id)
        .otherwiseIfEmpty(Mono.error(new PersonNotFoundException("not found")));
  }

  @ExceptionHandler
  public ResponseEntity<String> handNotFound(){
    return ResponseEntity.notFound().build();
  }


  @GetMapping("/issues")
  public Mono<String> issues(Model model){
    return dashboardService.findReactorIssues().collectList()
        .then(issues->{
          model.addAttribute("issues", issues);

          return Mono.just("issues");
        });
  }

  @GetMapping("/reactorIssues")
  @ResponseBody
  public Flux<ReactorIssue> reactorIssues(){
    return dashboardService.findReactorIssues();
  }


  @GetMapping("/chat")
  public String chat(){
    return "chat";
  }

  @GetMapping(value = "/chatMessages", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Flux<GitterMessage> chatMessages(@RequestParam(required = false, defaultValue = "10") int limit){
    return dashboardService.getLatestChatMessages(limit);
  }

  @GetMapping(value = "/chatStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseBody
  public Flux<GitterMessage> chatStream(){
    return dashboardService.streamChatMessages();
  }

}
