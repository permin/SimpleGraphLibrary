package simplegraphlibrary;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.sun.tools.doclint.Entity.isin;

/**
 * Created by permin on 09/11/2016.
 */
public interface Digraph {
  final class Edge {

    public Edge(int source, int target) {
      this.source = source;
      this.target = target;
    }

    public String toString() {
      return "(" + getSource() + " -> " + this.getTarget() + ")";
    }

    public int getSource() {
      return source;
    }

    public int getTarget() {
      return target;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || this.getClass() != o.getClass()) {
        return false;
      }
      Digraph.Edge edge = (Digraph.Edge) o;
      return this.source == edge.source &&
          this.target == edge.target;
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.source, this.target);
    }

    final private int source;
    final private int target;
  }

  int verticesNumber();

  Set<Integer> allVertices();

  List<Digraph.Edge> outgoingEdges(int vertex);

  Iterable<Digraph.Edge> allEdges();

  List<Integer> targetsOfOutgoingEdges(int vertex);
}
