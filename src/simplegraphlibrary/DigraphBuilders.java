package simplegraphlibrary;

/**
 * Created by permin on 09/11/2016.
 */
public class DigraphBuilders {
  private DigraphBuilders() {
  }

  public static DigraphBuilder adjacencyListsDigraphBuilder() {
    return new AdjacencyListsDigraph.Builder();
  }

  public static DigraphBuilder adjacencyListsSymmetricDigraphBuilder() {
    return new AdjacencyListsDigraph.SymmetricBuilder();
  }
}

