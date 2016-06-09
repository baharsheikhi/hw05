/**
 * Created by baharsheikhi on 6/7/16.
 * invariants: pitch must be one of the 12 notes, and a duration can't be more than 4
 * octave: 0-9
 */
//TODO how should I handle rests in the toString?
public class Note {

    public enum Pitch {
        C, CSHARP, D, DSHARP, E, F, FSHARP, G, GSHARP, A, ASHARP, B, REST;

        public String pitchtoString() {
            StringBuffer ret = new StringBuffer();
            switch (this) {
                case C:
                    ret.append("  C  ");
                    break;
                case CSHARP:
                    ret.append(" C♯  ");
                    break;
                case D:
                    ret.append("  D  ");
                    break;
                case DSHARP:
                    ret.append(" D♯  ");
                    break;
                case E:
                    ret.append("  E  ");
                    break;
                case F:
                    ret.append("  F  ");
                    break;
                case FSHARP:
                    ret.append(" F♯  ");
                    break;
                case G:
                    ret.append("  G  ");
                    break;
                case GSHARP:
                    ret.append(" G♯  ");
                    break;
                case A:
                    ret.append("  A  ");
                    break;
                case ASHARP:
                    ret.append(" A♯  ");
                    break;
                case B:
                    ret.append("  B  ");
                    break;
                case REST:
                    ret.append(" ");
                default:
                    break;
            }
                return ret.toString();
        }
    }


    /**
     * Creates a note with the given pitch and duration
     * @param pitch The pitch of a note
     * @param duration The duration of the note
     */
    public Note(Pitch pitch, int duration, int octave) {
        if (duration < 0 || duration > 4) {
            throw new IllegalArgumentException("Please enter a valid duration");
        }

        if (octave < 0 || octave > 9) {
            throw new IllegalArgumentException("Please enter a valid octave number");
        }
        this.pitch = pitch;
        this.duration = duration;
        this.octave = octave;
    }

    @Override
    public String toString() {
        String ret = "  X  ";

        for (int i = 0; i < this.duration; i++) {
            ret+="  |  ";
        }

        return ret;
    }

    private final Pitch pitch;
    private final int duration;
    private final int octave;

}
