package simplegraphlibrary;

import java.util.ArrayList;
import java.util.List;

import simplegraphlibrary.DigraphTraversals.AbstractVisitor;
import simplegraphlibrary.DigraphTraversals.GenerateDiscoverOrderVisitor;
import simplegraphlibrary.DigraphTraversals.Visitor;
import simplegraphlibrary.VerticesPartition.Builder;

/**
 * Created by permin on 16/11/2016.
 */
public class ConnectivityAlgorithms {
  private ConnectivityAlgorithms() {
  }


  static public Partition<Integer> findStronglyConnectedComponents(Digraph digraph) {
    List<Integer> discoverOrder = new ArrayList<>();
    Visitor discoverOrderGenerator = new GenerateDiscoverOrderVisitor(discoverOrder);
    DigraphTraversals.traverseInDepthFirstSearchOrder(digraph, discoverOrderGenerator);

    Digraph transposedDigraph = GraphUtils.transposeDigraph(digraph);
    Builder connectedComponentsBuilder = new Builder();
    Visitor stronglyConnectedComponentsGenerator =
        new ConnectivityAlgorithms.StronglyConnectedComponentsGenerator(connectedComponentsBuilder);
    //Collections.reverse(discoverOrder);
    System.err.println(discoverOrder);
    DigraphTraversals.traverseInDepthFirstSearchOrder(discoverOrder,
        transposedDigraph,
        stronglyConnectedComponentsGenerator);
    return connectedComponentsBuilder.build();
  }

  private static class StronglyConnectedComponentsGenerator extends AbstractVisitor {

    private final Builder verticesPartitionBuilder;
    private Integer firstVertexInCompoment;
    private Integer componentIndex;

    private StronglyConnectedComponentsGenerator(Builder
                                                     verticesPartition) {
      verticesPartitionBuilder = verticesPartition;
    }

    @Override
    public void discoverVertex(int vertex) {
      if (this.firstVertexInCompoment == null) {
        this.firstVertexInCompoment = vertex;
        this.componentIndex = this.verticesPartitionBuilder.createNewGroup();
      }
      this.verticesPartitionBuilder.addToGroup(this.componentIndex, vertex);
    }

    @Override
    public void finishVertex(int vertex) {
      if (vertex == this.firstVertexInCompoment) {
        this.firstVertexInCompoment = null;
      }
    }
  }
}
