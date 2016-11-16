package simplegraphlibrary;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by permin on 16/11/2016.
 */
public class AdjacencyListsDigraphTest {
  @Test
  public void DigraphCreation1() {
    DigraphBuilder builder = DigraphBuilders.adjacencyListsDigraphBuilder();
    builder.setVerticesNumber(5);
    builder.addEdge(0, 2);
    builder.addEdge(3, 2);
    builder.addEdge(3, 2);
    Digraph digraph = builder.build();
    assertThat(digraph.targetsOfOutgoingEdges(0), equalTo(Arrays.asList(2)));
    assertThat(digraph.targetsOfOutgoingEdges(3), equalTo(Arrays.asList(2, 2)));
    assertThat("Vertices number", digraph.verticesNumber() == 5);
    {
      Set<Integer> verticesSet = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4));
      assertThat(digraph.allVertices(), equalTo(verticesSet));
    }
    for (int vertex : digraph.allVertices()) {
      if (vertex == 0 || vertex == 3) {
        continue;
      }
      assertThat(digraph.targetsOfOutgoingEdges(vertex), equalTo(Collections.EMPTY_LIST));
    }
    {
      Set<Digraph.Edge> allEdges = new HashSet<>();
      for (Digraph.Edge edge : digraph.allEdges()) {
        allEdges.add(edge);
      }
      assertThat("number of unique edges", allEdges.size(), equalTo(2));
      {
        int numberOfEdges = 0;
        for (Digraph.Edge edge : digraph.allEdges()) {
          ++numberOfEdges;
        }
        assertThat(numberOfEdges, equalTo(3));
      }
      assertThat(digraph.allEdges(), not(hasItems(new Digraph.Edge(2, 3))));
      assertThat(digraph.allEdges(), hasItems(new Digraph.Edge(3, 2)));
      assertThat(digraph.allEdges(), hasItems(new Digraph.Edge(0, 2)));
    }
  }

  @Test
  public void DigraphCreation2() {
    DigraphBuilder builder = DigraphBuilders.adjacencyListsSymmetricDigraphBuilder();
    builder.setVerticesNumber(5);
    builder.addEdge(0, 2);
    builder.addEdge(3, 2);
    builder.addEdge(3, 2);
    Digraph digraph = builder.build();
    assertThat(digraph.targetsOfOutgoingEdges(2), equalTo(Arrays.asList(0, 3, 3)));
    assertThat(digraph.targetsOfOutgoingEdges(0), equalTo(Arrays.asList(2)));
    assertThat(digraph.targetsOfOutgoingEdges(3), equalTo(Arrays.asList(2, 2)));
    assertThat("Vertices number", digraph.verticesNumber() == 5);
    {
      Set<Integer> verticesSet = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4));
      assertThat(digraph.allVertices(), equalTo(verticesSet));
    }
    assertThat(digraph.targetsOfOutgoingEdges(1), equalTo(Collections.EMPTY_LIST));
    assertThat(digraph.targetsOfOutgoingEdges(4), equalTo(Collections.EMPTY_LIST));
    {
      Set<Digraph.Edge> allEdges = new HashSet<>();
      for (Digraph.Edge edge : digraph.allEdges()) {
        allEdges.add(edge);
      }
      assertThat("number of unique edges", allEdges.size(), equalTo(4));
      {
        int numberOfEdges = 0;
        for (Digraph.Edge edge : digraph.allEdges()) {
          ++numberOfEdges;
        }
        assertThat(numberOfEdges, equalTo(6));
      }
      assertThat(digraph.allEdges(), hasItems(new Digraph.Edge(2, 3)));
      assertThat(digraph.allEdges(), hasItems(new Digraph.Edge(3, 2)));
      assertThat(digraph.allEdges(), hasItems(new Digraph.Edge(0, 2)));
      assertThat(digraph.allEdges(), hasItems(new Digraph.Edge(2, 0)));
    }
  }
}