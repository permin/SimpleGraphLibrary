package simplegraphlibrary;

public abstract class AbstractPartition<E> implements Partition<E> {
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AbstractPartition{");
    for (int groupIndex = 0; groupIndex < groupsNumber(); ++groupIndex) {
      if (groupIndex > 0) {
        builder.append("; ");
      }
      builder.append(group(groupIndex));
    }
    builder.append("}");
    return builder.toString();
  }
}
