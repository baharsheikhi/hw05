/**
 * Created by baharsheikhi on 6/7/16.
 */
public class MusicEditorModelImpl implements MusicEditorModel<Note> {
    @Override
    public String getMusicState() {
        return null;
    }

    @Override
    public void addNote(int beat, Note note) {

    }

    @Override
    public void removeNote(int beat, Note note) {

    }

    @Override
    public void changeNote(int beat, Note oldNote, Note newNote) {

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
        return 0;
    }

    @Override
    public void trim(int from, int to) {

    }

    @Override
    public void addBeats() {

    }

    public MusicEditorModelImpl() {
    }
}
