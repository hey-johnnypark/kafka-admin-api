package com.github.heyjohnnypark.controller;

import com.github.heyjohnnypark.model.Topic;
import com.github.heyjohnnypark.services.TopicService;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

  @Autowired
  private TopicService topicService;

  @GetMapping(path = "/topics")
  public Collection<Topic> getAllTopics() throws ExecutionException, InterruptedException {
    return topicService.getAllTopics();
  }

  @GetMapping(path = "/topics/{topicName}")
  public Topic getTopic(
      @PathVariable(name = "topicName", required = true) String topicName)
      throws ExecutionException, InterruptedException {
    return topicService.getTopic(topicName);
  }


  @PostMapping(path = "/topics")
  public String createTopic(@Valid @RequestBody(required = true) Topic topic) {
    try {
      topicService.createTopic(topic);
      return "true";
    } catch (ExecutionException e) {
      e.printStackTrace();
      return "false";
    } catch (InterruptedException e) {
      e.printStackTrace();
      return "false";
    }

  }

  @DeleteMapping("/topics/{topicName}")
  public String deleteTopic(@PathVariable(name = "topicName", required = true) String topicName) {
    try {
      topicService.deleteTopic(topicName);
      return "true";
    } catch (ExecutionException e) {
      e.printStackTrace();
      return "false";
    } catch (InterruptedException e) {
      e.printStackTrace();
      return "false";
    }

  }


}
