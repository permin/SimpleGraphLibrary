package simplegraphlibrary;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by permin on 09/11/2016.
 */
public interface Digraph {
  int verticesNumber();

  Set<Integer> allVertices();

  List<Edge> outgoingEdges(int vertex);

  Iterable<Edge> allEdges();

  List<Integer> targetsOfOutgoingEdges(int vertex);

  final class Edge {

    private final int source;
    private final int target;

    public Edge(int source, int target) {
      this.source = source;
      this.target = target;
    }

    public int getSource() {
      return this.source;
    }

    public int getTarget() {
      return this.target;
    }

    @Override
    public int hashCode() {
      return Objects.hash(source, target);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Edge edge = (Edge) o;
      return source == edge.source &&
          target == edge.target;
    }

    public String toString() {
      return "(" + this.getSource() + " -> " + getTarget() + ")";
    }
  }
}
