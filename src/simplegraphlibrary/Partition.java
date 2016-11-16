package simplegraphlibrary;

/**
 * Created by permin on 16/11/2016.
 */
public interface Partition<E> {
  int groupIndex(E element);

  Iterable<E> group(int groupIndex);

  int groupsNumber();

  interface Builder<E> {
    int createNewGroup();
    void addToGroup(int groupIndex, E element);
    Partition<E> build();
  }
}

