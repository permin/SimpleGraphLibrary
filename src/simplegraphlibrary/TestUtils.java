package simplegraphlibrary;

import java.util.Random;

/**
 * Created by permin on 24/11/2016.
 */
class TestUtils {
  static class RandomSimpleDigraphGenerator {
    private final double edgeProbability;
    private final TestUtils.RandomSimpleDigraphGenerator.SelfLoops selfLoopsPolicy;

    RandomSimpleDigraphGenerator(double edgeProbability, TestUtils.RandomSimpleDigraphGenerator.SelfLoops selfLoopsPolicy) {
      this.edgeProbability = edgeProbability;
      this.selfLoopsPolicy = selfLoopsPolicy;
    }

    Digraph generate(int verticesNumber) {
      DigraphBuilder builder = DigraphBuilders.adjacencyListsDigraphBuilder();
      builder.setVerticesNumber(verticesNumber);
      Random generator = new Random();
      for (int i = 0; i < verticesNumber; ++i) {
        for (int j = 0; j < verticesNumber; ++j) {
          if (i == j && this.selfLoopsPolicy == TestUtils.RandomSimpleDigraphGenerator.SelfLoops.FORBIDDEN) {
            continue;
          }
          if (generator.nextDouble() <= this.edgeProbability) {
            builder.addEdge(i, j);
          }
        }
      }
      return builder.build();
    }

    public enum SelfLoops {ALLOWED, FORBIDDEN}
  }
}
