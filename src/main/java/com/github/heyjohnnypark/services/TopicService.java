package com.github.heyjohnnypark.services;

import com.github.heyjohnnypark.model.Topic;
import com.github.heyjohnnypark.model.Topic.TopicBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

  @Autowired
  private AdminClient adminClient;

  public Collection<Topic> getAllTopics() throws ExecutionException, InterruptedException {
    return adminClient
        .describeTopics(fetchAllTopicNames())
        .all()
        .get()
        .values()
        .stream()
        .map(topicDesc -> TopicBuilder
            .aTopic()
            .withName(topicDesc.name())
            .withPartitions(topicDesc.partitions().size())
            .withReplicationFactor((short) topicDesc.partitions().get(0).replicas().size())
            .build())
        .sorted(TopicHelper::compareByName)
        .collect(Collectors.toList());
  }

  public Topic getTopic(String topicName)
      throws ExecutionException, InterruptedException {
    return adminClient
        .describeTopics(Arrays.asList(topicName))
        .all()
        .get()
        .values()
        .stream()
        .map(topicDesc -> TopicBuilder
            .aTopic()
            .withName(topicDesc.name())
            .withPartitions(topicDesc.partitions().size())
            .withReplicationFactor((short) topicDesc.partitions().get(0).replicas().size())
            .build())
        .sorted(TopicHelper::compareByName)
        .collect(Collectors.toList())
        .get(0);
  }


  public void createTopic(Topic topic) throws ExecutionException, InterruptedException {
    adminClient
        .createTopics(Arrays.asList(TopicHelper.fromTopic(topic)))
        .values()
        .get(topic.getName())
        .get();
  }

  public void deleteTopic(String name) throws ExecutionException, InterruptedException {
    adminClient.deleteTopics(Arrays.asList(name))
        .values()
        .get(name)
        .get();
  }


  private Collection<String> fetchAllTopicNames() throws ExecutionException, InterruptedException {
    return adminClient
        .listTopics()
        .names()
        .get();
  }

}
