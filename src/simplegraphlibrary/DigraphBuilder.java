package simplegraphlibrary;

public interface DigraphBuilder {
  DigraphBuilder setVerticesNumber(int verticesNumber);

  DigraphBuilder addEdge(int source, int target);

  Digraph build();
}