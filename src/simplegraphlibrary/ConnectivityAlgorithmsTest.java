package simplegraphlibrary;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import simplegraphlibrary.DigraphTraversals.AbstractVisitor;
import simplegraphlibrary.TestUtils.RandomSimpleDigraphGenerator;
import simplegraphlibrary.TestUtils.RandomSimpleDigraphGenerator.SelfLoops;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by permin on 16/11/2016.
 */
public class ConnectivityAlgorithmsTest {
  @Test
  public void findStronglyConnectedComponents() {
    for (int test = 0; test < 1000; ++test) {
      Random randomGenerator = new Random();
      int verticesNumber = randomGenerator.nextInt(20);
      for (double edgeProbability : Arrays.asList(0.0, 0.03, 0.1, 0.3, 0.5, 0.7, 1.0)) {
        RandomSimpleDigraphGenerator generator = new RandomSimpleDigraphGenerator(edgeProbability, SelfLoops.FORBIDDEN);
        Digraph digraph = generator.generate(verticesNumber);
        Partition<Integer> stronglyConnectedComponents = ConnectivityAlgorithms.findStronglyConnectedComponents(digraph);
        boolean isReachable[][] = new boolean[verticesNumber][verticesNumber];
        for (int v = 0; v < verticesNumber; ++v) {
          int finalV = v;
          DigraphTraversals.traverseInDepthFirstSearchOrder(v, digraph, new
              AbstractVisitor() {
                @Override
                public void discoverVertex(int vertex) {
                  isReachable[finalV][vertex] = true;
                }
              });
        }
        for (int v = 0; v < verticesNumber; ++v) {
          for (int v2 = 0; v2 < verticesNumber; ++v2) {
            boolean inOneConnectedComponent = isReachable[v][v2] && isReachable[v2][v];
            assertEquals(inOneConnectedComponent, stronglyConnectedComponents.groupIndex(v) ==
                stronglyConnectedComponents.groupIndex(v2));
          }
        }
      }
    }
  }

  @Test
  public void contractGraphTest() {
    VerticesPartition verticesPartition = null;
    Digraph digraph = null;
    {
      DigraphBuilder builder = DigraphBuilders.adjacencyListsDigraphBuilder();
      builder.setVerticesNumber(5);
      builder.addEdge(0, 2);
      builder.addEdge(0, 3);
      builder.addEdge(1, 0);
      digraph = builder.build();
    }
    {
      VerticesPartition.Builder partitionBuilder = new VerticesPartition.Builder();
      int firstGroup = partitionBuilder.createNewGroup();
      int secondGroup = partitionBuilder.createNewGroup();
      int thirdGroup = partitionBuilder.createNewGroup();
      partitionBuilder.addToGroup(firstGroup, 0);
      partitionBuilder.addToGroup(firstGroup, 2);
      partitionBuilder.addToGroup(firstGroup, 3);
      partitionBuilder.addToGroup(secondGroup, 1);
      partitionBuilder.addToGroup(thirdGroup, 4);
      verticesPartition = partitionBuilder.build();
    }
    Digraph contractedDigraph = ConnectivityAlgorithms.contractDigraph(digraph, verticesPartition);
    assertEquals(contractedDigraph.verticesNumber(), 3);
    assertThat(contractedDigraph.allEdges(), hasItems(new Digraph.Edge(1, 0)));

    List<Digraph.Edge> edges = new ArrayList<Digraph.Edge>();
    for (Digraph.Edge edge : contractedDigraph.allEdges()) {
      edges.add(edge);
    }
    assertEquals(edges.size(), 1);
  }
}