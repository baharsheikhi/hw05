import java.util.Comparator;

/**
 * Created by bahar on 6/8/16.
 */
public class NoteComparator implements Comparator<Note>{
  @Override
  public int compare(Note o1, Note o2) {
    return o1.compareTo(o2);
  }
}
