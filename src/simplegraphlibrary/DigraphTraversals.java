package simplegraphlibrary;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by permin on 13/11/2016.
 */
public class DigraphTraversals {
  private DigraphTraversals() {
  }

  public interface Visitor {
    void discoverVertex(int vertex);

    void examineEdge(Digraph.Edge edge);

    void treeEdge(Digraph.Edge edge);

    void finishVertex(int vertex);
  }

  public static class CombinedVisitor implements Visitor {
    private final Visitor firstVisitor;
    private final Visitor secondVisitor;

    public CombinedVisitor(Visitor firstVisitor, Visitor secondVisitor) {
      this.firstVisitor = firstVisitor;
      this.secondVisitor = secondVisitor;
    }

    @Override
    public void discoverVertex(int vertex) {
      firstVisitor.discoverVertex(vertex);
      secondVisitor.discoverVertex(vertex);
    }

    @Override
    public void examineEdge(Digraph.Edge edge) {
      firstVisitor.examineEdge(edge);
      secondVisitor.examineEdge(edge);
    }

    @Override
    public void treeEdge(Digraph.Edge edge) {
      firstVisitor.treeEdge(edge);
      secondVisitor.treeEdge(edge);
    }

    @Override
    public void finishVertex(int vertex) {
      firstVisitor.finishVertex(vertex);
      secondVisitor.finishVertex(vertex);
    }
  }

  public static class AbstractVisitor implements Visitor {
    @Override
    public void discoverVertex(int vertex) {
    }

    @Override
    public void examineEdge(Digraph.Edge edge) {
    }

    @Override
    public void treeEdge(Digraph.Edge edge) {
    }

    @Override
    public void finishVertex(int vertex) {
    }
  }

  public static class FindPredecessorsVisitor extends AbstractVisitor {
    private final Map<Integer, Integer> predecessors;

    FindPredecessorsVisitor(Map<Integer, Integer> predecessors) {
      this.predecessors = predecessors;
    }

    @Override
    public void treeEdge(Digraph.Edge edge) {
      predecessors.put(edge.getTarget(), edge.getSource());
    }
  }

  public static class GenerateDiscoverOrderVisitor extends AbstractVisitor {
    final private List<Integer> discoverOrder;

    public GenerateDiscoverOrderVisitor(List<Integer> discoverOrder) {
      this.discoverOrder = discoverOrder;
    }

    @Override
    public void discoverVertex(int vertex) {
      discoverOrder.add(vertex);
    }
  }

  public static void traverseInDepthFirstSearchOrder(int startVertex, Digraph digraph, Visitor visitor) {
    Set<Integer> discoveredVertices = new HashSet<Integer>();
    traverseInDepthFirstSearchOrder(startVertex, digraph, visitor, discoveredVertices);
  }

  public static void traverseInDepthFirstSearchOrder(Iterable<Integer> verticesOrder, Digraph digraph, Visitor visitor) {
    Set<Integer> discoveredVertices = new HashSet<Integer>();
    for (int vertex : verticesOrder) {
      traverseInDepthFirstSearchOrder(vertex, digraph, visitor, discoveredVertices);
    }
  }

  public static void traverseInDepthFirstSearchOrder(Digraph digraph, Visitor visitor) {
    traverseInDepthFirstSearchOrder(digraph.allVertices(), digraph, visitor);
  }

  private static void traverseInDepthFirstSearchOrder(int vertex, Digraph digraph, Visitor visitor,
                                                      Set<Integer> discoveredVertices) {
    if (discoveredVertices.contains(vertex)) {
      return;
    }
    visitor.discoverVertex(vertex);
    discoveredVertices.add(vertex);
    for (Digraph.Edge edge : digraph.outgoingEdges(vertex)) {
      visitor.examineEdge(edge);
      if (!discoveredVertices.contains(edge.getTarget())) {
        visitor.treeEdge(edge);
      }
      DigraphTraversals.traverseInDepthFirstSearchOrder(edge.getTarget(), digraph, visitor, discoveredVertices);
    }
    visitor.finishVertex(vertex);
  }


  public static void traverseInBreadthFirstSearchOrder(int source,
                                                       Digraph digraph,
                                                       Map<Integer, Integer> distances,
                                                       DigraphTraversals.Visitor visitor) {
    List<Integer> sources = new ArrayList<Integer>();
    sources.add(source);
    DigraphTraversals.traverseInBreadthFirstSearchOrder(sources,
        digraph, distances, visitor);
  }

  public static void traverseInBreadthFirstSearchOrder(Iterable<Integer> sources,
                                                       Digraph digraph,
                                                       Map<Integer, Integer> distances,
                                                       DigraphTraversals.Visitor visitor) {
    Queue<Integer> verticesToProcess = new ArrayDeque<Integer>();
    for (int source : sources) {
      verticesToProcess.add(source);
      distances.put(source, 0);
      visitor.discoverVertex(source);
    }
    while (!verticesToProcess.isEmpty()) {
      int vertex = verticesToProcess.poll();
      for (Digraph.Edge edge : digraph.outgoingEdges(vertex)) {
        visitor.examineEdge(edge);
        int target = edge.getTarget();
        if (!distances.containsKey(target)) {
          visitor.treeEdge(edge);
          visitor.discoverVertex(target);
          distances.put(target, distances.get(vertex) + 1);
          verticesToProcess.add(target);
        }
      }
      visitor.finishVertex(vertex);
    }
  }
}
