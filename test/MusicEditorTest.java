import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by baharsheikhi on 6/6/16.
 */
public class MusicEditorTest {
    MusicEditorModel<Note> modelToAddRangePrint;
    MusicEditorModel<Note> model2ToAddBeatsPrint;

    public void initData() {
        this.modelToAddRangePrint = new MusicEditorModelImpl();
        this.model2ToAddBeatsPrint = new MusicEditorModelImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBaseCasePrint() {
        this.initData();
        modelToAddRangePrint.getMusicState();
    }

    @Test
    public void testNoNotesBeatLength() {
        this.initData();
        //was originally 0
        assertEquals(8, this.modelToAddRangePrint.beatLength());
    }

    @Test
    public void testSimpleAddPrint() {
        this.initData();
        //FIXME
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
//        assertEquals(" C4   C#4  D4  D#4   E4   F4  F#4   G4  \n" +
//                     " 0                   X                  \n" +
//                     " 1                   |                  \n" +
//                     " 2                   |                  \n", modelToAddRangePrint.getMusicState());

        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        assertEquals(this.modelToAddRangePrint.getMusicState(), this.model2ToAddBeatsPrint.getMusicState());
        //changed
        assertEquals(8, this.modelToAddRangePrint.beatLength());
    }

    @Test
    public void testOverlapNote() {
        this.initData();
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        this.modelToAddRangePrint.addNote(2, new Note(Note.Pitch.E, 2, 4, 0, true));
        assertEquals(" C4   C#4  D4  D#4   E4   F4  F#4   G4  \n" +
                     " 0                   X                  \n" +
                     " 1                   |                  \n" +
                     " 2                   X                  \n" +
                     " 3                   |                  \n", modelToAddRangePrint.getMusicState());
        assertEquals(8, this.modelToAddRangePrint.beatLength());
    }

    @Test
    public void testAddRangePrint() {
        this.initData();
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        this.modelToAddRangePrint.addNote(2, new Note(Note.Pitch.E, 2, 4, 0, true));
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.CSHARP, 4, 9, 0, true));
        assertEquals(" C4   C#4  D4  D#4   E4   F4  F#4   G4   C5   C#5  D5  D#5   E5   F5  F#5   G5   C6   C#6  D6  D#6   E6   F6  F#6   G6   C7   C#7  D7  D#7   E7   F7  F#7   G7   C8   C#8  D8  D#8   E8   F8  F#8   G8   C9   C#9  D9  D#9   E9   F9  F#9   G9  \n" +
                     " 0                   X                                                                                                                                                                                         X                                \n" +
                     " 1                   |                                                                                                                                                                                         |                                \n" +
                     " 2                   X                                                                                                                                                                                         |                                \n" +
                     " 3                   |                                                                                                                                                                                         |                                \n"
                                                                                                                                                                                                                                                                        , modelToAddRangePrint.getMusicState());
        assertEquals(8, this.modelToAddRangePrint.beatLength());

    }

    @Test
    public void testAddBeatsPrint() {
        this.initData();
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.DSHARP, 3, 4, 0, true));
        assertEquals(8, model2ToAddBeatsPrint.beatLength());
        this.model2ToAddBeatsPrint.addBeats(4);
        assertEquals(12, model2ToAddBeatsPrint.beatLength());

        assertEquals(" C4   C#4  D4  D#4   E4   F4  F#4   G4  \n" +
                " 0                   X                  \n" +
                " 1                   |                  \n" +
                " 2                   |                  \n" +
                " 3                                      \n" +
                " 4                                      \n" +
                " 5                                      \n" +
                " 6                                      \n" +
                " 7                                      \n", model2ToAddBeatsPrint.getMusicState());

        this.model2ToAddBeatsPrint.addNote(4, new Note(Note.Pitch.E, 4, 4, 4, true));
        assertEquals(" C4   C#4  D4  D#4   E4   F4  F#4   G4  \n" +
                " 0                   X                       \n" +
                " 1                   |                       \n" +
                " 2                   |                       \n" +
                " 3                                           \n" +
                " 4                        X                  \n" +
                " 5                        |                  \n" +
                " 6                        |                  \n" +
                " 7                        |                   \n", model2ToAddBeatsPrint.getMusicState());
        assertEquals(7, model2ToAddBeatsPrint.beatLength());
    }


    //Exceptions for adding
    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteBeatNoExist() {
        this.initData();
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.DSHARP, 3, 4, 0, true));
        assertEquals(8, model2ToAddBeatsPrint.beatLength());
        //this should throw an exception
        this.model2ToAddBeatsPrint.addNote(4, new Note(Note.Pitch.E, 4, 4, 4, true));
        //this should throw an exception
        this.model2ToAddBeatsPrint.addNote(-1, new Note(Note.Pitch.DSHARP, 7, 4, -1, true));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNoteNoFit() {
        this.initData();
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.DSHARP, 4, 4, 0, true));
        this.model2ToAddBeatsPrint.addNote(4, new Note(Note.Pitch.DSHARP, 4, 4, 0, true));
        this.model2ToAddBeatsPrint.addNote(6, new Note(Note.Pitch.DSHARP, 4, 4, 0, true));
    }

    //Exceptions for removing
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNoteNonExistentNote() {
        this.initData();
        this.model2ToAddBeatsPrint.removeNote(5, new Note(Note.Pitch.DSHARP, 4, 4, 5, true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNoteInvalidBeat() {
        this.initData();
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.DSHARP, 3, 4, 0, true));
        this.model2ToAddBeatsPrint.removeNote(-1, new Note(Note.Pitch.DSHARP, 4, 4, -1, true));
        this.model2ToAddBeatsPrint.removeNote(5, new Note(Note.Pitch.DSHARP, 4, 4, 5, true));
    }

    @Test
    public void testBasicRemove() {
        this.initData();
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.DSHARP, 3, 4, 0, true));
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.DSHARP, 3, 4, 0, true));
        assertEquals(this.model2ToAddBeatsPrint.getMusicState(), this.modelToAddRangePrint.getMusicState());
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        assertNotEquals(this.model2ToAddBeatsPrint.getMusicState(), this.modelToAddRangePrint.getMusicState());
        this.model2ToAddBeatsPrint.removeNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        assertEquals(this.model2ToAddBeatsPrint.getMusicState(), this.modelToAddRangePrint.getMusicState());
    }

    @Test
    public void intermediate() {
        this.initData();
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.G, 3, 6, 0, true));
        assertEquals(8, this.modelToAddRangePrint.beatLength());
        this.modelToAddRangePrint.addBeats(5);
        this.modelToAddRangePrint.addNote(4, new Note(Note.Pitch.FSHARP, 2, 6, 4, true));
        this.modelToAddRangePrint.addNote(7, new Note(Note.Pitch.A, 3, 4, 0, true));
        this.modelToAddRangePrint.addNote(4, new Note(Note.Pitch.DSHARP, 3, 5, 4, true));
        this.modelToAddRangePrint.removeNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 3, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));
//        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 4, 7, 0));

        System.out.print(this.modelToAddRangePrint.getMusicState());

    }




}
