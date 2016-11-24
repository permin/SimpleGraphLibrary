package simplegraphlibrary;

import java.util.Iterator;

import simplegraphlibrary.Digraph.Edge;

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
      this.vertex = 0;
    }

    @Override
    public boolean hasNext() {
      return this.vertex < this.verticesNumber;
    }

    @Override
    public Integer next() {
      return this.vertex++;
    }
  }

  static class EdgesTargetIterator implements Iterator<Integer> {
    private final Iterator<Edge> edgeIterator;

    public EdgesTargetIterator(Iterator<Edge> edgeIterator) {
      this.edgeIterator = edgeIterator;
    }

    @Override
    public boolean hasNext() {
      return this.edgeIterator.hasNext();
    }

    @Override
    public Integer next() {
      return this.edgeIterator.next().getTarget();
    }
  }
}
