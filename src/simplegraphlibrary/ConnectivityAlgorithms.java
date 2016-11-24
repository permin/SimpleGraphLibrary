package simplegraphlibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by permin on 16/11/2016.
 */
public class ConnectivityAlgorithms {
  private ConnectivityAlgorithms() {
  }


  public static Partition<Integer> findStronglyConnectedComponents(Digraph digraph) {
    List<Integer> exitOrder = new ArrayList<>();
    DigraphTraversals.Visitor exitOrderGenerator = new DigraphTraversals.ExitOrderGenerator(exitOrder);
    DigraphTraversals.traverseInDepthFirstSearchOrder(digraph, exitOrderGenerator);

    Digraph transposedDigraph = GraphUtils.transposeDigraph(digraph);
    VerticesPartition.Builder connectedComponentsBuilder = new VerticesPartition.Builder();
    DigraphTraversals.Visitor stronglyConnectedComponentsGenerator =
        new StronglyConnectedComponentsGenerator(connectedComponentsBuilder);
    Collections.reverse(exitOrder);
    DigraphTraversals.traverseInDepthFirstSearchOrder(exitOrder,
        transposedDigraph,
        stronglyConnectedComponentsGenerator);
    return connectedComponentsBuilder.build();
  }

  private static class StronglyConnectedComponentsGenerator extends DigraphTraversals.AbstractVisitor {

    private final VerticesPartition.Builder verticesPartitionBuilder;
    private Integer firstVertexInComponent;
    private Integer componentIndex;

    private StronglyConnectedComponentsGenerator(VerticesPartition.Builder
                                                     verticesPartition) {
      this.verticesPartitionBuilder = verticesPartition;
    }

    @Override
    public void discoverVertex(int vertex) {
      if (firstVertexInComponent == null) {
        firstVertexInComponent = vertex;
        componentIndex = verticesPartitionBuilder.createNewGroup();
      }
      verticesPartitionBuilder.addToGroup(componentIndex, vertex);
    }

    @Override
    public void finishVertex(int vertex) {
      if (vertex == firstVertexInComponent) {
        firstVertexInComponent = null;
      }
    }
  }
}
