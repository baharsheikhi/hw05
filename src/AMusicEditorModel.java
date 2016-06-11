import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by baharsheikhi on 6/10/16.
 */
public  abstract class AMusicEditorModel implements MusicEditorModel<Note> {

    @Override
    public int beatLength() {
        return beatNumberToRender;
    }

    @Override
    public void addNote(int beat, Note newNote) {
        if (beat < 0 || beat > this.beatLength()) {
            throw new IllegalArgumentException("Please enter a valid beat number");
        }

        if (newNote.getDuration() + beat > this.beatLength()) {
            this.addBeats(newNote.getDuration());
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
    public void addToMusic(MusicEditorModel<Note> model, int place) {
        if (model == null) {
            throw new IllegalArgumentException("Please make sure model is not null");
        }
        if (place < 0 || place > model.beatLength()) {
            throw new IllegalArgumentException("Please enter a valid place to add the music");
        }
        for (int i = 0; i < this.notes.size(); i++) {
            for (int n = 0; n < this.notes.get(i).size(); n++) {
                if (this.notes.get(i).get(n).isHead()) {
                    try {
                        model.addNote(place + i, notes.get(i).get(n));
                    } catch (IllegalArgumentException e) {
                        model.addBeats(this.notes.size() * 2);
                        model.addNote(place + i, notes.get(i).get(n));
                    }
                }
            }
        }
    }

    @Override
    public void trim(int from, int to) {
        if (from < 0 || to < 0 || from > this.beatLength() || to > this.beatLength() || from > to) {
            throw new IllegalArgumentException("Please enter valid start and end points");
        }
        List<ArrayList<Note>> sublist = this.notes.subList(from, to);
        this.notes.removeAll(sublist);
        this.beatNumberToRender -= to - from;
    }

    private final ArrayList<ArrayList<Note>> notes;
    private  int beatNumberToRender;

    protected Note getNote(int outer, int inner) {
        return this.notes.get(outer).get(inner);
    }


    protected void addBeatNumberToRender(int increase) {
        this.beatNumberToRender += increase;
    }

    protected void trimToSize() {
        int i = this.beatLength();
        while (i < this.notes.size()) {
            if (notes.get(i).size() != 0) {
                throw new IllegalArgumentException("cannot trim properly.");
            }
            notes.remove(i);
        }
    }
    protected abstract void expandInteralBeats(int increase);
    protected void addBeatToNotes(ArrayList<Note> beatRow) {
        this.notes.add(beatRow);
    }

    protected int notesSize() {
        return this.notes.size();
    }

    protected int notesAtBeatSize(int beatNumber) {
        return this.notes.get(beatNumber).size();
    }

    public AMusicEditorModel() {
        this.notes = new ArrayList<ArrayList<Note>>();
        for (int i = 0; i < 10; i++) {
            notes.add(new ArrayList<Note>());
        }
        this.beatNumberToRender = 0;
    }
}
