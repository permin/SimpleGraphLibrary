package simplegraphlibrary;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import simplegraphlibrary.GraphImplementationUtils.ContinuousVerticesRangeIterator;

/**
 * Created by permin on 09/11/2016.
 */
public class AdjacencyListsDigraph extends AbstractDigraph {
  private final int verticesNumber;

  private final List<List<Digraph.Edge>> outgoingEdges;

  private AdjacencyListsDigraph(int verticesNumber, List<List<Digraph.Edge>> outgoingEdges) {
    this.verticesNumber = verticesNumber;
    this.outgoingEdges = outgoingEdges;
  }

  @Override
  public Set<Integer> allVertices() {
    return new AbstractSet<Integer>() {
      @Override
      public Iterator<Integer> iterator() {
        return new ContinuousVerticesRangeIterator(AdjacencyListsDigraph.this.verticesNumber);
      }

      @Override
      public int size() {
        return AdjacencyListsDigraph.this.verticesNumber;
      }
    };
  }

  @Override
  public List<Digraph.Edge> outgoingEdges(int vertex) {
    return this.outgoingEdges.get(vertex);
  }

  static class Builder implements DigraphBuilder {
    private int verticesNumber;
    private List<List<Digraph.Edge>> outgoingEdges;

    @Override
    public DigraphBuilder setVerticesNumber(int verticesNumber) {
      this.verticesNumber = verticesNumber;
      this.outgoingEdges = new ArrayList<List<Digraph.Edge>>();
      for (int i = 0; i < verticesNumber; ++i) {
        this.outgoingEdges.add(new ArrayList<Digraph.Edge>());
      }
      return this;
    }

    @Override
    public DigraphBuilder addEdge(int source, int target) {
      this.outgoingEdges.get(source).add(new Digraph.Edge(source, target));
      return this;
    }

    @Override
    public Digraph build() {
      return new AdjacencyListsDigraph(this.verticesNumber, this.outgoingEdges);
    }
  }

  static class SymmetricBuilder implements DigraphBuilder {
    private final AdjacencyListsDigraph.Builder builder = new AdjacencyListsDigraph.Builder();

    @Override
    public DigraphBuilder setVerticesNumber(int verticesNumber) {
      this.builder.setVerticesNumber(verticesNumber);
      return this;
    }

    @Override
    public DigraphBuilder addEdge(int source, int target) {
      this.builder.addEdge(source, target);
      if (source != target) {
        this.builder.addEdge(target, source);
      }
      return this;
    }

    @Override
    public Digraph build() {
      return this.builder.build();
    }
  }
}
