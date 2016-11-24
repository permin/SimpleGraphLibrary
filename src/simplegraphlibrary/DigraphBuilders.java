package simplegraphlibrary;

import simplegraphlibrary.AdjacencyListsDigraph.Builder;
import simplegraphlibrary.AdjacencyListsDigraph.SimpleDigraphBuilder;
import simplegraphlibrary.AdjacencyListsDigraph.SymmetricBuilder;

/**
 * Created by permin on 09/11/2016.
 */
public class DigraphBuilders {
  private DigraphBuilders() {
  }

  public static DigraphBuilder adjacencyListsDigraphBuilder() {
    return new Builder();
  }

  public static DigraphBuilder adjacencyListsSymmetricDigraphBuilder() {
    return new SymmetricBuilder();
  }

  public static DigraphBuilder simpleDigraphBuilder() {
    return new SimpleDigraphBuilder();
  }
}

