package com.github.heyjohnnypark.controller;

import com.github.heyjohnnypark.model.ClusterInfo;
import com.github.heyjohnnypark.model.ClusterNode;
import com.github.heyjohnnypark.services.ClusterService;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClusterController {

  @Autowired
  private ClusterService clusterService;

  @GetMapping("/cluster")
  public ClusterInfo getClusterInfo() throws ExecutionException, InterruptedException {
    return clusterService.getClusterInfo();
  }

  @GetMapping("/cluster/nodes")
  public Collection<ClusterNode> getClusterNodes() throws ExecutionException, InterruptedException {
    return clusterService.getNodes();
  }

  @GetMapping("/cluster/nodes/{nodeId}")
  public ClusterNode getNodeInfo(@PathVariable(name = "nodeId") String nodeId)
      throws ExecutionException, InterruptedException {
    return clusterService.getNodeById(nodeId);
  }

  @GetMapping("/cluster/nodes/leader")
  public ClusterNode getNodeInfo()
      throws ExecutionException, InterruptedException {
    return clusterService.getLeaderNode();
  }

}
