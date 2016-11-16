package simplegraphlibrary;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by permin on 12/11/2016.
 */
public abstract class AbstractDigraph implements Digraph {
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Digraph with ").append(this.verticesNumber()).append(" vertices ");
    builder.append(this.allVertices());
    builder.append("; adjacent vertices are {");

    for (Iterator<Integer> it = this.allVertices().iterator(); it.hasNext(); ) {
      int vertex = it.next();
      builder.append(vertex).append(": ");
      builder.append(this.targetsOfOutgoingEdges(vertex));
      if (it.hasNext()) {
        builder.append("; ");
      }
    }
    builder.append("}");
    return builder.toString();
  }

  @Override
  public Iterable<Edge> allEdges() {
    return new Iterable<Edge>() {
      @Override
      public Iterator<Edge> iterator() {
        return new Iterator<Edge>() {

          private Iterator<Integer> vertexIterator = allVertices().iterator();
          private Iterator<Edge> edgesIterator = null;

          @Override
          public boolean hasNext() {
            skipEmptyLists();
            return edgesIterator != null && edgesIterator.hasNext();
          }

          @Override
          public Edge next() {
            skipEmptyLists();
            return edgesIterator.next();
          }

          private void skipEmptyLists() {
            while ((edgesIterator == null || !edgesIterator.hasNext()) && vertexIterator.hasNext()) {
              edgesIterator = outgoingEdges(vertexIterator.next()).iterator();
            }
          }
        };
      }
    };
  }

  @Override
  public int verticesNumber() {
    return this.allVertices().size();
  }

  @Override
  public List<Integer> targetsOfOutgoingEdges(int vertex) {
    return new AbstractList<Integer>() {
      @Override
      public Integer get(int index) {
        return AbstractDigraph.this.outgoingEdges(vertex).get(index).getTarget();
      }

      @Override
      public Iterator<Integer> iterator() {
        return new GraphImplementationUtils.EdgesTargetIterator(AbstractDigraph.this.outgoingEdges(vertex).iterator());
      }

      @Override
      public int size() {
        return AbstractDigraph.this.outgoingEdges(vertex).size();
      }
    };
  }
}
