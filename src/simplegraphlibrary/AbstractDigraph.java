package simplegraphlibrary;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

import simplegraphlibrary.GraphImplementationUtils.EdgesTargetIterator;

/**
 * Created by permin on 12/11/2016.
 */
public abstract class AbstractDigraph implements Digraph {
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Digraph with ").append(verticesNumber()).append(" vertices ");
    builder.append(allVertices());
    builder.append("; adjacent vertices are {");

    for (Iterator<Integer> it = allVertices().iterator(); it.hasNext(); ) {
      int vertex = it.next();
      builder.append(vertex).append(": ");
      builder.append(targetsOfOutgoingEdges(vertex));
      if (it.hasNext()) {
        builder.append("; ");
      }
    }
    builder.append("}");
    return builder.toString();
  }

  @Override
  public int verticesNumber() {
    return allVertices().size();
  }

  @Override
  public Iterable<Digraph.Edge> allEdges() {
    return new Iterable<Digraph.Edge>() {
      @Override
      public Iterator<Digraph.Edge> iterator() {
        return new Iterator<Digraph.Edge>() {

          private final Iterator<Integer> vertexIterator = AbstractDigraph.this.allVertices().iterator();
          private Iterator<Digraph.Edge> edgesIterator;

          @Override
          public boolean hasNext() {
            this.skipEmptyLists();
            return this.edgesIterator != null && this.edgesIterator.hasNext();
          }

          @Override
          public Digraph.Edge next() {
            this.skipEmptyLists();
            return this.edgesIterator.next();
          }

          private void skipEmptyLists() {
            while ((this.edgesIterator == null || !this.edgesIterator.hasNext()) && this.vertexIterator.hasNext()) {
              this.edgesIterator = AbstractDigraph.this.outgoingEdges(this.vertexIterator.next()).iterator();
            }
          }
        };
      }
    };
  }

  @Override
  public List<Integer> targetsOfOutgoingEdges(int vertex) {
    return new AbstractList<Integer>() {
      @Override
      public Integer get(int index) {
        return outgoingEdges(vertex).get(index).getTarget();
      }

      @Override
      public Iterator<Integer> iterator() {
        return new EdgesTargetIterator(outgoingEdges(vertex).iterator());
      }

      @Override
      public int size() {
        return outgoingEdges(vertex).size();
      }
    };
  }
}
