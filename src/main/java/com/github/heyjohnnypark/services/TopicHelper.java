package com.github.heyjohnnypark.services;

import com.github.heyjohnnypark.model.Topic;
import org.apache.kafka.clients.admin.NewTopic;

public class TopicHelper {

  private TopicHelper() {
  }

  public static NewTopic fromTopic(Topic topic) {
    return new NewTopic(topic.getName(), topic.getPartitions(), topic.getReplicationFactor());
  }

  public static int compareByName(Topic a, Topic b) {
    return a.getName().compareTo(b.getName());
  }

}
