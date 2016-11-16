package simplegraphlibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerticesPartition extends AbstractPartition<Integer> {

  final private List<List<Integer>> groups;
  final private Map<Integer, Integer> groupIndex;

  VerticesPartition(List<List<Integer>> groups, Map<Integer, Integer> groupIndex) {
    this.groups = groups;
    this.groupIndex = groupIndex;
  }

  @Override
  public int groupIndex(Integer element) {
    return groupIndex.get(element);
  }

  @Override
  public Iterable<Integer> group(int groupIndex) {
    return groups.get(groupIndex);
  }

  @Override
  public int groupsNumber() {
    return groups.size();
  }

  static public class Builder implements Partition.Builder<Integer>{

    final private List<List<Integer>> groups = new ArrayList<>();
    final private Map<Integer, Integer> groupIndex = new HashMap<>();

    @Override
    public int createNewGroup() {
      groups.add(new ArrayList<>());
      return groups.size() - 1;
    }

    @Override
    public void addToGroup(int groupIndex, Integer element) {
      this.groupIndex.put(element, groupIndex);
      groups.get(groupIndex).add(element);
    }

    @Override
    public VerticesPartition build() {
      return new VerticesPartition(groups, groupIndex);
    }
  }
}
