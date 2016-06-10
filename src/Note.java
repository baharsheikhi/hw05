import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by baharsheikhi on 6/7/16. invariants: pitch must be one of the 12 notes, and a duration
 * can't be more than 4 octave: 0-9
 */

public class Note implements Comparable<Note> {

  public enum Pitch {
    C, CSHARP, D, DSHARP, E, F, FSHARP, G, GSHARP, A, ASHARP, B;

    public String pitchtoString() {
      StringBuffer ret = new StringBuffer();
      switch (this) {
        case C:
          ret.append(" C");
          break;
        case CSHARP:
          ret.append("C♯");
          break;
        case D:
          ret.append(" D");
          break;
        case DSHARP:
          ret.append("D♯");
          break;
        case E:
          ret.append(" E");
          break;
        case F:
          ret.append(" F");
          break;
        case FSHARP:
          ret.append("F♯");
          break;
        case G:
          ret.append(" G");
          break;
        case GSHARP:
          ret.append("G♯");
          break;
        case A:
          ret.append(" A");
          break;
        case ASHARP:
          ret.append("A♯");
          break;
        case B:
          ret.append(" B");
          break;
        default:
          break;
      }
      return ret.toString();
    }

      private Pitch getNextPitch() {
          Pitch ret = null;
          switch (this) {
              case C:
                  return CSHARP;
              case CSHARP:
                  return D;
              case D:
                  return DSHARP;
              case DSHARP:
                  return E;
              case E:
                  return F;
              case F:
                  return FSHARP;
              case FSHARP:
                  return G;
              case G:
                  return GSHARP;
              case GSHARP:
                  return A;
              case A:
                  return ASHARP;
              case ASHARP:
                  return B;
              case B:
                  return C;
              default:
          }
          return ret;
      }

  }


  /**
   * Creates a note with the given pitch and duration
   *
   * @param pitch    The pitch of a note
   * @param duration The duration of the note
   */
  public Note(Pitch pitch, int duration, int octave, int beat, boolean isHead) {
    if (duration < 0 || duration > 4) {
      throw new IllegalArgumentException("Please enter a valid duration");
    }

    if (octave < 0) {
      throw new IllegalArgumentException("Please enter a valid octave number");
    }

    if (beat < 0) {
      throw new IllegalArgumentException("Please enter a valid beat number");
    }
    this.pitch = pitch;
    this.duration = duration;
    this.octave = octave;
    this.beat = beat;
      this.isHead = isHead;
  }

  public String getNoteState() {
    if (isHead) {
        return "  X  ";
    }
      else {
        return "  |  ";
    }

  }

  /**
   * Returns a String representation of this Note's Pitch
   *
   * @return the pitch as a String
   */
  @Override
  public String toString() {
    return this.pitch.pitchtoString() + Integer.toString(octave);
  }

  public static String pitchRender(Pitch pitch, int octave) {
    return " "+ pitch.pitchtoString() + Integer.toString(octave) + " ";
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Note)) {
      return false;
    }
    else {
      Note otherNote = (Note) other;
      return (this.pitch == otherNote.pitch) && (this.duration == otherNote.duration) &&
              (this.octave == otherNote.octave) && (this.beat == otherNote.beat);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pitch, this.duration, this.octave, this.beat);
  }

  @Override
  public int compareTo(Note o) {
    if (this.octave - o.octave == 0) {
      return this.pitch.ordinal() - o.pitch.ordinal();
    }
    else {
      return this.octave - o.octave;
    }
  }

  private final Pitch pitch;
  private final int duration;
  private final int octave;
  private final int beat;
    private final boolean isHead;

  protected int getOctave() {
    return this.octave;
  }

  protected int getDuration() {
    return this.duration;
  }

  protected Pitch getPitch() {
    return this.pitch;
  }

    protected Note getNextNote() {
        if (this.pitch == Pitch.B) {
            return new Note(Pitch.C, this.duration, this.octave + 1, this.beat, this.isHead);
        }
        else {
            return new Note(this.pitch.getNextPitch(), this.duration, this.octave, this.beat, this.isHead);
        }
    }

    protected boolean isHead() {
        return this.isHead;
    }

}
