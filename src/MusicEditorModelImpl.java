import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by baharsheikhi on 6/7/16.
 */
public class MusicEditorModelImpl implements MusicEditorModel<Note> {
  @Override
  public String getMusicState() {
    if (this.notes.get(0).size() == 0) {
      throw new IllegalArgumentException("add notes to display the editor");
    }

    StringBuffer ret = new StringBuffer();
    //pads first row appropriately
    for (int i = 0; i < (int) (Math.log10(renderNoteLength) + 1); i++) {
      ret.append(" ");
    }

    ret.append(this.renderPitchNotes());

    //pads the row with the zero appropriately
    for (int i = 0; i < (int) (Math.log10(renderNoteLength) + 1) - 1; i++) {
      ret.append(" ");
    }
    ret.append("0\n");


    //starts padding an arbritrary amount of notes
    for (int i = 1; i < this.renderNoteLength; i++) {
      //pads the beginning of each row appropriately
      for (int j = 0; j < (((int) Math.log10(renderNoteLength)) - ((int) Math.log10(i))); j++) {
        ret.append(" ");
      }
      ret.append(Integer.toString(i) + "\n");
    }

    return ret.toString();

  }

  @Override
  public void addNote(int beat, Note newNote) {
    if (newNote.getOctave() > maxOctave) {
      maxOctave = newNote.getOctave();
    }
    if (newNote.getOctave() < minOctave) {
      minOctave = newNote.getOctave();
    }
    this.renderNoteLength += newNote.getDuration();
    this.notes.get(beat).add(newNote);
    Collections.shuffle(this.notes.get(beat));
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

  /**
   * Constructs an empty list of notes
   */
  public MusicEditorModelImpl() {
    this.notes = new ArrayList<ArrayList<Note>>();
    for (int i = 0; i < 10; i++) {
      notes.add(new ArrayList<Note>());
    }
    this.maxOctave = Integer.MIN_VALUE;
    this.minOctave = Integer.MAX_VALUE;
    this.noteLength = 10;
    this.renderNoteLength = 0;
  }

  private final ArrayList<ArrayList<Note>> notes;
  int maxOctave;
  int minOctave;
  int noteLength;
  int renderNoteLength;

  protected String renderPitchNotes() {
    StringBuffer ret = new StringBuffer();
    for (int i = minOctave; i < maxOctave + 1; i++) {
      ret.append(Note.pitchRender(Note.Pitch.C, i));
      ret.append(Note.pitchRender(Note.Pitch.CSHARP, i));
      ret.append(Note.pitchRender(Note.Pitch.D, i));
      ret.append(Note.pitchRender(Note.Pitch.DSHARP, i));
      ret.append(Note.pitchRender(Note.Pitch.E, i));
      ret.append(Note.pitchRender(Note.Pitch.F, i));
      ret.append(Note.pitchRender(Note.Pitch.FSHARP, i));
      ret.append(Note.pitchRender(Note.Pitch.G, i));
      ret.append(Note.pitchRender(Note.Pitch.GSHARP, i));
      ret.append(Note.pitchRender(Note.Pitch.A, i));
      ret.append(Note.pitchRender(Note.Pitch.ASHARP, i));
      ret.append(Note.pitchRender(Note.Pitch.B, i));
    }
    ret.append("\n");
    return ret.toString();
  }
}
