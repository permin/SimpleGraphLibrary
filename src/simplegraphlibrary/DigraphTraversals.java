package simplegraphlibrary;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import simplegraphlibrary.Digraph.Edge;

/**
 * Created by permin on 13/11/2016.
 */
public class DigraphTraversals {
  private DigraphTraversals() {
  }

  public static void traverseInDepthFirstSearchOrder(int startVertex, Digraph digraph, DigraphTraversals.Visitor visitor) {
    DigraphTraversals.traverseInDepthFirstSearchOrder(Arrays.asList(startVertex), digraph, visitor);
  }

  public static void traverseInDepthFirstSearchOrder(Iterable<Integer> verticesOrder, Digraph digraph, DigraphTraversals.Visitor visitor) {
    Set<Integer> discoveredVertices = new HashSet<Integer>();
    for (int vertex : verticesOrder) {
      DigraphTraversals.traverseInDepthFirstSearchOrder(vertex, digraph, visitor, discoveredVertices);
    }
  }

  public static void traverseInDepthFirstSearchOrder(Digraph digraph, DigraphTraversals.Visitor visitor) {
    DigraphTraversals.traverseInDepthFirstSearchOrder(digraph.allVertices(), digraph, visitor);
  }

  private static void traverseInDepthFirstSearchOrder(int vertex, Digraph digraph, DigraphTraversals.Visitor visitor,
                                                      Set<Integer> discoveredVertices) {
    if (discoveredVertices.contains(vertex)) {
      return;
    }
    visitor.discoverVertex(vertex);
    discoveredVertices.add(vertex);
    for (Edge edge : digraph.outgoingEdges(vertex)) {
      visitor.examineEdge(edge);
      if (!discoveredVertices.contains(edge.getTarget())) {
        visitor.treeEdge(edge);
      }
      traverseInDepthFirstSearchOrder(edge.getTarget(), digraph, visitor, discoveredVertices);
    }
    visitor.finishVertex(vertex);
  }

  public static void traverseInBreadthFirstSearchOrder(int source,
                                                       Digraph digraph,
                                                       Map<Integer, Integer> distances,
                                                       DigraphTraversals.Visitor visitor) {
    List<Integer> sources = new ArrayList<Integer>();
    sources.add(source);
    traverseInBreadthFirstSearchOrder(sources,
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
      for (Edge edge : digraph.outgoingEdges(vertex)) {
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

  public interface Visitor {
    void discoverVertex(int vertex);

    void examineEdge(Edge edge);

    void treeEdge(Edge edge);

    void finishVertex(int vertex);
  }

  public static class CombinedVisitor implements DigraphTraversals.Visitor {
    private final DigraphTraversals.Visitor firstVisitor;
    private final DigraphTraversals.Visitor secondVisitor;

    public CombinedVisitor(DigraphTraversals.Visitor firstVisitor, DigraphTraversals.Visitor secondVisitor) {
      this.firstVisitor = firstVisitor;
      this.secondVisitor = secondVisitor;
    }

    @Override
    public void discoverVertex(int vertex) {
      this.firstVisitor.discoverVertex(vertex);
      this.secondVisitor.discoverVertex(vertex);
    }

    @Override
    public void examineEdge(Edge edge) {
      this.firstVisitor.examineEdge(edge);
      this.secondVisitor.examineEdge(edge);
    }

    @Override
    public void treeEdge(Edge edge) {
      this.firstVisitor.treeEdge(edge);
      this.secondVisitor.treeEdge(edge);
    }

    @Override
    public void finishVertex(int vertex) {
      this.firstVisitor.finishVertex(vertex);
      this.secondVisitor.finishVertex(vertex);
    }
  }

  public static class AbstractVisitor implements DigraphTraversals.Visitor {
    @Override
    public void discoverVertex(int vertex) {
    }

    @Override
    public void examineEdge(Edge edge) {
    }

    @Override
    public void treeEdge(Edge edge) {
    }

    @Override
    public void finishVertex(int vertex) {
    }
  }

  public static class FindPredecessorsVisitor extends DigraphTraversals.AbstractVisitor {
    private final Map<Integer, Integer> predecessors;

    FindPredecessorsVisitor(Map<Integer, Integer> predecessors) {
      this.predecessors = predecessors;
    }

    @Override
    public void treeEdge(Edge edge) {
      this.predecessors.put(edge.getTarget(), edge.getSource());
    }
  }

  public static class DiscoverOrderGenerator extends DigraphTraversals.AbstractVisitor {
    private final List<Integer> discoverOrder;

    public DiscoverOrderGenerator(List<Integer> discoverOrder) {
      this.discoverOrder = discoverOrder;
    }

    @Override
    public void discoverVertex(int vertex) {
      this.discoverOrder.add(vertex);
    }
  }

  public static class ExitOrderGenerator extends DigraphTraversals.AbstractVisitor {
    private final List<Integer> exitOrder;

    public ExitOrderGenerator(List<Integer> exitOrder) {
      this.exitOrder = exitOrder;
    }

    @Override
    public void finishVertex(int vertex) {
      this.exitOrder.add(vertex);
    }
  }
}
