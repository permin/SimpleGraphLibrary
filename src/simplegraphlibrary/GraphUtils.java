package simplegraphlibrary;

/**
 * Created by permin on 16/11/2016.
 */
public class GraphUtils {
  private GraphUtils() {
  }

  public static Digraph transposeDigraph(Digraph digraph) {
    DigraphBuilder builder = DigraphBuilders.adjacencyListsDigraphBuilder();
    builder.setVerticesNumber(digraph.verticesNumber());
    for (Digraph.Edge edge : digraph.allEdges()) {
      // reverse edge
      builder.addEdge(edge.getTarget(), edge.getSource());
    }
    return builder.build();
  }
}
