package simplegraphlibrary;

import java.util.Iterator;

/**
 * Created by permin on 09/11/2016.
 */
class GraphImplementationUtils {
  private GraphImplementationUtils() {
  }

  static class ContinuousVerticesRangeIterator implements Iterator<Integer> {
    private final int verticesNumber;
    private int vertex;

    ContinuousVerticesRangeIterator(int verticesNumber) {
      this.verticesNumber = verticesNumber;
      vertex = 0;
    }

    @Override
    public boolean hasNext() {
      return vertex < verticesNumber;
    }

    @Override
    public Integer next() {
      return vertex++;
    }
  }

  static class EdgesTargetIterator implements Iterator<Integer> {
    private final Iterator<Digraph.Edge> edgeIterator;

    public EdgesTargetIterator(Iterator<Digraph.Edge> edgeIterator) {
      this.edgeIterator = edgeIterator;
    }

    @Override
    public boolean hasNext() {
      return edgeIterator.hasNext();
    }

    @Override
    public Integer next() {
      return edgeIterator.next().getTarget();
    }
  }
}
