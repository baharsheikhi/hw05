
import java.util.*;

/**
 * Created by baharsheikhi on 6/7/16.
 * invariant: notelength represents the size of the external array. Is positive
 * rendernotelength represents how many of these notes to render. Is positive.
 */
public class MusicEditorModelImpl implements MusicEditorModel<Note> {
    @Override
    public String getMusicState() {
        //is this the best way to tell?
        if (this.notes.get(0).size() == 0) {
            throw new IllegalArgumentException("add notes to display the editor");
        }

        StringBuffer ret = new StringBuffer();
        //pads first row appropriately
        for (int i = 0; i < (int) (Math.log10(beatNumberToRender) + 1); i++) {
            ret.append(" ");
        }

        ret.append(this.renderPitchNotes());
        ret.append(" ");


        //starts padding an arbritrary amount of notes
        for (int i = 0; i < this.beatNumberToRender; i++) {
            //pads the beginning of each row appropriately
            for (int j = 0; j < (((int) Math.log10(beatNumberToRender)) - ((int) Math.log10(i))); j++) {
                ret.append(" ");
            }
            ret.append(Integer.toString(i));
            if (this.notes.get(i).size() != 0) {
                this.padMusicStateHelp(ret, this.minNote, this.notes.get(i).get(0), 0);
                for (int n = 1; n < this.notes.get(i).size(); n++) {
                    this.padMusicStateHelp(ret, this.notes.get(i).get(n - 1), this.notes.get(i).get(n), n);
                }
            }

            ret.append("\n");

        }

        return ret.toString();

    }

    @Override
    public void addNote(int beat, Note newNote) {
        if (beat < 0 || beat > this.beatLength()) {
            throw new IllegalArgumentException("Please enter a valid beat number");
        }
        if (newNote.getDuration() + beat > this.beatNumberToRender) {
            throw new IllegalArgumentException("Note doesn't fit");
        }

        if (newNote.compareTo(minNote) < 0) {
            minNote = newNote;
        } else if ((maxNote == null || newNote.compareTo(maxNote) > 0) && newNote.compareTo(minNote) != 0) {
            maxNote = newNote;
        }

        this.notes.get(beat).add(newNote);
        Collections.sort(this.notes.get(beat));
        for (int i = 1; i < newNote.getDuration(); i++) {
            this.notes.get(beat + i).add(new Note(newNote.getPitch(), newNote.getDuration(), newNote.getOctave(), beat, false));
            Collections.sort(this.notes.get(beat + i));
        }
    }

    @Override
    public void removeNote(int beat, Note note) {
        if (beat < 0 || beat > this.beatLength() - 1) {
            throw new IllegalArgumentException("Enter valid beat");
        }
        if (this.notes.get(beat).remove(note) == false) {
            throw new IllegalArgumentException("Please enter a valid note to remove");
        }
        for (int i = 0; i < note.getDuration(); i++) {
            this.notes.get(beat + i).remove(note);
        }

    }

    @Override
    public void changeNote(int beat, Note oldNote, Note newNote) {
        this.removeNote(beat, oldNote);
        this.addNote(beat, newNote);
    }

    @Override
    public MusicEditorModel<Note> musicConcat(MusicEditorModel<Note> other) {
        return null;
    }

    @Override
    public MusicEditorModel<Note> mixMusic(MusicEditorModel<Note> other, int from) {
        return null;
    }

    @Override
    public int beatLength() {
        return this.beatNumberToRender;
    }

    @Override
    public void trim(int from, int to) {
        List<ArrayList<Note>> newList = this.notes.subList(from, to);
        this.notes.clear();
        for (int i = 0; i < newList.size(); i++) {
            this.notes.set(i, newList.get(i));
        }

        //TODO update the instance variables and throw exceptions
    }

    @Override
    public void addBeats(int increase) {

        if ((this.beatNumberToRender + increase) >= internalBeats) {
            this.expandInteralBeats();
        }

        this.addBeatNumberToRender(increase);

    }

    /**
     * Constructs an empty list of notes
     */
    public MusicEditorModelImpl() {
        this.notes = new ArrayList<ArrayList<Note>>();
        for (int i = 0; i < 10; i++) {
            notes.add(new ArrayList<Note>());
        }
        this.maxNote = null;//new Note(Note.Pitch.C, 0, 0, 0);
        this.minNote = new Note(Note.Pitch.B, 0, Integer.MAX_VALUE, 0, true);
        this.internalBeats = 10;
        this.beatNumberToRender = 8;
    }

    private final ArrayList<ArrayList<Note>> notes;
    Note maxNote;
    Note minNote;
    int internalBeats;
    int beatNumberToRender;

    private String renderPitchNotes() {
        StringBuffer ret = new StringBuffer();
        ret.append(Note.pitchRender(this.minNote.getPitch(), this.minNote.getOctave()));
        if (this.maxNote != null) {
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

    private void expandInteralBeats() {
        for (int i = 0; i < internalBeats; i++) {
            notes.add(new ArrayList<Note>());
        }

        this.doubleInternalBeats();
    }

    private void doubleInternalBeats() {
        internalBeats = internalBeats * 2;
    }

    private void addBeatNumberToRender(int increase) {
        this.beatNumberToRender += increase;
    }

    private void padMusicStateHelp(StringBuffer buffer, Note prev, Note current, int n) {
        if (prev.getOctave() == current.getOctave()) {
            for (int p = 0; p < (current.getPitch().ordinal() - prev.getPitch().ordinal() - n); p++) {
                buffer.append("     ");
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
        }

        buffer.append(current.getNoteState());
    }

}
