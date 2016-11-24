package simplegraphlibrary;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import simplegraphlibrary.Digraph.Edge;
import simplegraphlibrary.DigraphTraversals.ExitOrderGenerator;
import simplegraphlibrary.DigraphTraversals.Visitor;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by permin on 16/11/2016.
 */
public class DigraphTraversalsTest {
  private static final int A = 7;
  private static final int B = 2;
  private static final int C = 3;
  private static final int D = 5;
  private static final int E = 9;
  private static final int F = 1;
  private static final int ISOLATED_VERTEX = 8;
  private Digraph digraph;

  @Before
  public void setUp() throws Exception {
    DigraphBuilder builder = DigraphBuilders.adjacencyListsDigraphBuilder();
    builder.setVerticesNumber(10);
    builder.addEdge(A, B);
    builder.addEdge(A, C);
    builder.addEdge(C, D);
    builder.addEdge(D, A);
    builder.addEdge(B, D);
    builder.addEdge(E, F);
    this.digraph = builder.build();
  }

  @Test
  public void traverseInDepthFirstSearchOrder() throws Exception {
    List<Integer> discoveredVertices = new ArrayList<>();
    DigraphTraversals.traverseInDepthFirstSearchOrder(this.digraph, new
        ExitOrderGenerator(discoveredVertices));
    assertThat("All vertices are discovered", discoveredVertices.containsAll(this.digraph.allVertices
        ()));
    assertThat(discoveredVertices.size(), equalTo(this.digraph.verticesNumber()));
  }

  @Test
  public void traverseInDepthFirstSearchOrder1() throws Exception {
    Stack<Integer> openedVertices = new Stack<>();
    DigraphTraversals.traverseInDepthFirstSearchOrder(this.digraph, new Visitor() {

      @Override
      public void discoverVertex(int vertex) {
        openedVertices.add(vertex);
      }

      @Override
      public void examineEdge(Edge edge) {
      }

      @Override
      public void treeEdge(Edge edge) {
      }

      @Override
      public void finishVertex(int vertex) {
        assertThat("there is at least one open vertex", not(openedVertices.empty()));
        assertThat(openedVertices.pop(), equalTo(vertex));
      }
    });
    assertThat("all vertices were closed", openedVertices.empty());
  }


  @Test
  public void traverseInDepthFirstSearchOrder2() throws Exception {
    List<Integer> discoveredVertices = new ArrayList<>();
    DigraphTraversals.traverseInDepthFirstSearchOrder(A, this.digraph,
        new DigraphTraversals.DiscoverOrderGenerator(discoveredVertices));
    assertThat(discoveredVertices.size(), equalTo(4));
    assertThat(discoveredVertices, hasItems(A, B, C, D));
    assertThat(discoveredVertices.get(0), equalTo(A));
    assertThat(discoveredVertices.indexOf(B), either(equalTo(1)).or(equalTo(3)));
    assertThat(discoveredVertices.indexOf(C), either(equalTo(1)).or(equalTo(3)));
    assertThat(discoveredVertices.indexOf(D), either(equalTo(2)).or(equalTo(3)));
  }

  @Test
  public void traverseInBreadthFirstSearchOrder1() throws Exception {
    Map<Integer, Integer> distances = new HashMap<>();
    DigraphTraversals.traverseInBreadthFirstSearchOrder(Arrays.asList(A, E, F, ISOLATED_VERTEX),
        digraph, distances,
        new
            DigraphTraversals.AbstractVisitor());
    assertThat(distances.size(), equalTo(7));
    assertTrue(distances.containsKey(A) &&
        distances.containsKey(B) &&
        distances.containsKey(C) &&
        distances.containsKey(D) &&
        distances.containsKey(ISOLATED_VERTEX) &&
        distances.containsKey(E) &&
        distances.containsKey(F));
    assertEquals(distances.get(ISOLATED_VERTEX), Integer.valueOf(0));
    assertEquals(distances.get(E), Integer.valueOf(0));
    assertEquals(distances.get(F), Integer.valueOf(0));
    assertEquals(distances.get(A), Integer.valueOf(0));
    assertEquals(distances.get(B), Integer.valueOf(1));
    assertEquals(distances.get(C), Integer.valueOf(1));
    assertEquals(distances.get(D), Integer.valueOf(2));
  }

  @Test
  public void traverseInBreadthFirstSearchOrder2() throws Exception {
    Map<Integer, Integer> distances = new HashMap<>();
    DigraphTraversals.traverseInBreadthFirstSearchOrder(A,
        digraph, distances,
        new
            DigraphTraversals.AbstractVisitor());
    assertThat(distances.size(), equalTo(4));
    assertTrue(distances.containsKey(A) &&
        distances.containsKey(B) &&
        distances.containsKey(C) &&
        distances.containsKey(D));
    assertEquals(distances.get(A), Integer.valueOf(0));
    assertEquals(distances.get(B), Integer.valueOf(1));
    assertEquals(distances.get(C), Integer.valueOf(1));
    assertEquals(distances.get(D), Integer.valueOf(2));
  }
}