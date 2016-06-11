
import java.util.*;

/**
 * Created by baharsheikhi on 6/7/16.
 * invariant: notelength represents the size of the external array. Is positive
 * rendernotelength represents how many of these notes to render. Is positive.
 */
public class MusicEditorModelImpl extends AMusicEditorModel {
    @Override
    public String getMusicState() {
        if (this.minNote.equals(new Note(Note.Pitch.B, 0, Integer.MAX_VALUE, 0, true)) && this.maxNote == null) {
            throw new IllegalArgumentException("add notes to display the editor");
        }

        StringBuffer ret = new StringBuffer();
        //pads first row appropriately
        for (int i = 0; i < (int) (Math.log10(this.beatLength()) + 1); i++) {
            ret.append(" ");
        }

        ret.append(this.renderPitchNotes());

        for (int i = 0; i < (int) (Math.log10(this.beatLength())); i++) {
            ret.append(" ");
        }



        //starts padding an arbritrary amount of notes
        for (int i = 0; i < this.beatLength(); i++) {
            //pads the beginning of each row appropriately
            for (int j = 0; j < (((int) Math.log10(beatLength())) - ((int) Math.log10(i))); j++) {
                ret.append(" ");
            }
            ret.append(Integer.toString(i));
            if (this.notesAtBeatSize(i) != 0) {
                this.padMusicStateHelp(ret, this.minNote, this.getNote(i, 0), 0);
                for (int n = 1; n < this.notesAtBeatSize(i); n++) {
                    this.padMusicStateHelp(ret, this.getNote(i, n-1), this.getNote(i, n), n);
                }
            }

            ret.append("\n");

        }
        return ret.toString();

    }

    @Override
    public void addNote(int beat, Note newNote) {

        if (newNote.compareTo(minNote) < 0) {
            minNote = newNote;
        } else if ((maxNote == null || newNote.compareTo(maxNote) > 0) && newNote.compareTo(minNote) != 0) {
            maxNote = newNote;
        }

        super.addNote(beat, newNote);
    }

    @Override
    public void removeNote(int beat, Note note) {
        if (note.equals(this.maxNote)) {
            this.maxNote = this.maxNote.getPrevNote();
        }
        if (note.equals(this.minNote)) {
            this.minNote = this.minNote.getNextNote();
        }
        super.removeNote(beat, note);
    }

    @Override
    public void musicConcat(MusicEditorModel<Note> other) {
        if (other == null) {
            throw new IllegalArgumentException("Please enter a a model that is not null");
        }
        this.trimToSize();
        other.addToMusic(this, this.beatLength());
    }

    @Override
    public void mixMusic(MusicEditorModel<Note> other, int from) {
        if (other == null) {
            throw new IllegalArgumentException("Please enter a model that is not null");
        }
        if (from < 0 || from > this.beatLength()) {
            throw new IllegalArgumentException("Please enter a valid starting point");
        }
        other.addToMusic(this, from);
    }


    @Override
    public void addBeats(int increase) {

        if ((this.beatLength() + increase) >= internalBeats) {
            //FIXME
            this.expandInteralBeats((int) Math.pow((double) increase, 2));
        }

        this.addBeatNumberToRender(increase);

    }


    /**
     * Constructs an empty list of notes
     */
    public MusicEditorModelImpl() {
        super();
        this.maxNote = null;//new Note(Note.Pitch.C, 0, 0, 0);
        this.minNote = new Note(Note.Pitch.B, 0, Integer.MAX_VALUE, 0, true);
        this.internalBeats = 10;
    }

    Note maxNote;
    Note minNote;
    int internalBeats;

    private String renderPitchNotes() {
        StringBuffer ret = new StringBuffer();
        ret.append(Note.pitchRender(this.minNote.getPitch(), this.minNote.getOctave()));
        if (this.maxNote != null && !this.maxNote.equals(this.minNote)) {
            Note n = this.minNote.getNextNote();
            while (n.compareTo(maxNote) < 0) {
                ret.append(Note.pitchRender(n.getPitch(), n.getOctave()));
                n = n.getNextNote();
            }
            ret.append(Note.pitchRender(this.maxNote.getPitch(), this.maxNote.getOctave()));
        }
        ret.append("\n");
        return ret.toString();
    }

    @Override
    protected void expandInteralBeats(int factor) {
        for (int i = 0; i < (internalBeats * factor); i++) {
            this.addBeatToNotes(new ArrayList<Note>());
        }

        this.doubleInternalBeats(factor);
    }

    private void doubleInternalBeats(int factor) {
        internalBeats = internalBeats * factor;
    }


    private void padMusicStateHelp(StringBuffer buffer, Note prev, Note current, int n) {
        if (prev.getOctave() == current.getOctave() && prev.equals(minNote)) {
            if (prev.getOctave() == current.getOctave() && !(prev.equals(current)) && prev !=minNote) {
                buffer.replace(buffer.lastIndexOf("  |  "), buffer.lastIndexOf("  |  "), current.getNoteState());
                buffer.deleteCharAt(buffer.lastIndexOf("  X  ") + 5);
                buffer.deleteCharAt(buffer.lastIndexOf("  X  ") + 6);
                buffer.deleteCharAt(buffer.lastIndexOf("  X  ") + 7);
            }
            else {
                for (int p = 0; p < (current.getPitch().ordinal() - prev.getPitch().ordinal() - n); p++) {
                    buffer.append("     ");
                }
                buffer.append(current.getNoteState());
            }
        } else {
            int octaveNumber = prev.getOctave();
            for (int p = 0; p < (12 - prev.getPitch().ordinal()); p++) {
                buffer.append("     ");
            }
            octaveNumber++;
            while (octaveNumber != current.getOctave()) {
                for (int p = 0; p < (12); p++) {
                    buffer.append("     ");
                }
                octaveNumber++;
            }

            for (int p = 0; p < (current.getPitch().ordinal() - Note.Pitch.C.ordinal() - n); p++) {
                buffer.append("     ");
            }
            buffer.append(current.getNoteState());
        }

    }


    @Override
    protected void trimToSize() {
        super.trimToSize();
        this.internalBeats = this.beatLength();
    }
}
