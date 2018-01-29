package com.github.heyjohnnypark.services;

import com.github.heyjohnnypark.model.ClusterNode;
import com.github.heyjohnnypark.model.Topic;
import com.github.heyjohnnypark.model.ClusterNode.ClusterNodeBuilder;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.Node;

public class ServiceHelper {

  private ServiceHelper() {
  }

  public static NewTopic fromTopic(Topic topic) {
    return new NewTopic(topic.getName(), topic.getPartitions(), topic.getReplicationFactor());
  }

  public static int compareByName(Topic a, Topic b) {
    return a.getName().compareTo(b.getName());
  }

  public static ClusterNode fromNode (Node node){
    return ClusterNodeBuilder.aClusterNode()
        .withHost(node.host())
        .withId(node.idString())
        .withPort(node.port())
        .withRack(node.rack())
        .build();
  }

}
