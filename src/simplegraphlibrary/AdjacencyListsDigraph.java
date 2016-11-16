package simplegraphlibrary;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by permin on 09/11/2016.
 */
public class AdjacencyListsDigraph extends AbstractDigraph implements Digraph {
  private final int verticesNumber;

  private final List<List<Edge>> outgoingEdges;

  private AdjacencyListsDigraph(int verticesNumber, List<List<Edge>> outgoingEdges) {
    this.verticesNumber = verticesNumber;
    this.outgoingEdges = outgoingEdges;
  }

  @Override
  public Set<Integer> allVertices() {
    return new AbstractSet<Integer>() {
      @Override
      public Iterator<Integer> iterator() {
        return new GraphImplementationUtils.ContinuousVerticesRangeIterator(verticesNumber);
      }

      @Override
      public int size() {
        return verticesNumber;
      }
    };
  }

  @Override
  public List<Edge> outgoingEdges(int vertex) {
    return outgoingEdges.get(vertex);
  }

  static class Builder implements DigraphBuilder {
    private int verticesNumber;
    private List<List<Edge>> outgoingEdges;

    @Override
    public DigraphBuilder setVerticesNumber(int verticesNumber) {
      this.verticesNumber = verticesNumber;
      outgoingEdges = new ArrayList<List<Edge>>();
      for (int i = 0; i < verticesNumber; ++i) {
        outgoingEdges.add(new ArrayList<Edge>());
      }
      return this;
    }

    @Override
    public DigraphBuilder addEdge(int source, int target) {
      outgoingEdges.get(source).add(new Edge(source, target));
      return this;
    }

    @Override
    public Digraph build() {
      return new AdjacencyListsDigraph(verticesNumber, outgoingEdges);
    }
  }

  static class SymmetricBuilder implements DigraphBuilder {
    final private Builder builder = new Builder();

    @Override
    public DigraphBuilder setVerticesNumber(int verticesNumber) {
      builder.setVerticesNumber(verticesNumber);
      return this;
    }

    @Override
    public DigraphBuilder addEdge(int source, int target) {
      builder.addEdge(source, target);
      if (source != target) {
        builder.addEdge(target, source);
      }
      return this;
    }

    @Override
    public Digraph build() {
      return builder.build();
    }
  }
}
