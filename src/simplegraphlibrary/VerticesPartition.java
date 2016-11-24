package simplegraphlibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerticesPartition extends AbstractPartition<Integer> {

  private final List<List<Integer>> groups;
  private final Map<Integer, Integer> groupIndex;

  VerticesPartition(List<List<Integer>> groups, Map<Integer, Integer> groupIndex) {
    this.groups = groups;
    this.groupIndex = groupIndex;
  }

  @Override
  public int groupIndex(Integer element) {
    return this.groupIndex.get(element);
  }

  @Override
  public Iterable<Integer> group(int groupIndex) {
    return this.groups.get(groupIndex);
  }

  @Override
  public int groupsNumber() {
    return this.groups.size();
  }

  public static class Builder implements Partition.Builder<Integer> {

    private final List<List<Integer>> groups = new ArrayList<>();
    private final Map<Integer, Integer> groupIndex = new HashMap<>();

    @Override
    public int createNewGroup() {
      this.groups.add(new ArrayList<>());
      return this.groups.size() - 1;
    }

    @Override
    public void addToGroup(int groupIndex, Integer element) {
      this.groupIndex.put(element, groupIndex);
      this.groups.get(groupIndex).add(element);
    }

    @Override
    public VerticesPartition build() {
      return new VerticesPartition(this.groups, this.groupIndex);
    }
  }
}
