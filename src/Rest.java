import java.util.Comparator;
import java.util.Objects;

/**
 * Created by baharsheikhi on 6/10/16.
 */
public class Rest extends Sound implements Comparable<Rest> {

    public Rest(int duration) {
        super(duration);
    }

    @Override
    public String getNoteState() {
        return "     ";
    }

    @Override
    public String toString() {
        return "     ";
    }

    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof Rest)) {
            return false;
        }
        else {
            Rest otherRest = (Rest) that;
            return otherRest.duration == this.duration;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }

    @Override
    public int compareTo(Rest other) {
        return this.duration - other.duration;
    }
}
