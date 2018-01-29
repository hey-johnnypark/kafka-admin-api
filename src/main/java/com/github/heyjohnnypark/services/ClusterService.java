package com.github.heyjohnnypark.services;

import com.github.heyjohnnypark.model.ClusterInfo;
import com.github.heyjohnnypark.model.ClusterInfo.ClusterInfoBuilder;
import com.github.heyjohnnypark.model.ClusterNode;
import com.github.heyjohnnypark.model.ClusterNode.ClusterNodeBuilder;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClusterService {

  @Autowired
  private AdminClient adminClient;

  public ClusterInfo getClusterInfo() throws ExecutionException, InterruptedException {

    DescribeClusterResult call = adminClient.describeCluster();

    ClusterInfoBuilder builder = ClusterInfoBuilder.aClusterInfo()
        .withClusterId(call.clusterId().get());

    call
        .nodes()
        .get()
        .forEach(node ->
            builder
                .addNode(ServiceHelper.fromNode(node))
        );

    builder.withLeaderNodeId(call.controller().get().idString());

    return builder.build();
  }

  public ClusterNode getNodeById(String id) throws ExecutionException, InterruptedException {

    DescribeClusterResult call = adminClient.describeCluster();

    return call
        .nodes()
        .get()
        .stream()
        .filter(node -> id.equals(node.idString()))
        .map(node -> ServiceHelper.fromNode(node))
        .findFirst()
        .orElseGet(null);
  }

  public ClusterNode getLeaderNode() throws ExecutionException, InterruptedException {
    return ServiceHelper.fromNode(adminClient.describeCluster().controller().get());
  }


}
