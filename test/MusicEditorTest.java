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
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        assertEquals("   E4 \n" +
                "0  X  \n" +
                "1  |  \n" +
                "2  |  \n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n", modelToAddRangePrint.getMusicState());

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
        assertEquals("   E4 \n" +
                "0  X  \n" +
                "1  |  \n" +
                "2  X    \n" +
                "3  |  \n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n", modelToAddRangePrint.getMusicState());
        assertEquals(8, this.modelToAddRangePrint.beatLength());
    }

    @Test
    public void testOverlapNoteRemove() {
        this.initData();
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        this.modelToAddRangePrint.addNote(2, new Note(Note.Pitch.E, 2, 4, 0, true));
        this.modelToAddRangePrint.removeNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        assertEquals("   E4 \n" +
                "0\n" +
                "1\n" +
                "2  X  \n" +
                "3  |  \n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n", this.modelToAddRangePrint.getMusicState());

    }

    @Test
    public void testAddRangePrint() {
        this.initData();
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.E, 3, 4, 0, true));
        this.modelToAddRangePrint.addNote(2, new Note(Note.Pitch.E, 2, 4, 0, true));
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.CSHARP, 4, 9, 0, true));
        assertEquals("   E4   F4  F♯4   G4  G♯4   A4  A♯4   B4   C5  C♯5   D5  D♯5   E5   F5  F♯5   G5  G♯5   A5  A♯5   B5   C6  C♯6   D6  D♯6   E6   F6  F♯6   G6  G♯6   A6  A♯6   B6   C7  C♯7   D7  D♯7   E7   F7  F♯7   G7  G♯7   A7  A♯7   B7   C8  C♯8   D8  D♯8   E8   F8  F♯8   G8  G♯8   A8  A♯8   B8   C9  C♯9 \n" +
                "0  X                                                                                                                                                                                                                                                                                            X  \n" +
                "1  |                                                                                                                                                                                                                                                                                            |  \n" +
                "2  X                                                                                                                                                                                                                                                                                            |  \n" +
                "3  |                                                                                                                                                                                                                                                                                            |  \n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n", modelToAddRangePrint.getMusicState());
        assertEquals(8, this.modelToAddRangePrint.beatLength());

    }

    @Test
    public void testAddBeatsPrint() {
        this.initData();
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.DSHARP, 3, 4, 0, true));
        assertEquals(8, model2ToAddBeatsPrint.beatLength());
        this.model2ToAddBeatsPrint.addBeats(4);
        assertEquals(12, model2ToAddBeatsPrint.beatLength());

        assertEquals("   D♯4 \n" +
                " 0  X  \n" +
                " 1  |  \n" +
                " 2  |  \n" +
                " 3\n" +
                " 4\n" +
                " 5\n" +
                " 6\n" +
                " 7\n" +
                " 8\n" +
                " 9\n" +
                "10\n" +
                "11\n", model2ToAddBeatsPrint.getMusicState());

        this.model2ToAddBeatsPrint.addNote(4, new Note(Note.Pitch.E, 4, 4, 4, true));
        assertEquals("   D♯4   E4 \n" +
                " 0  X  \n" +
                " 1  |  \n" +
                " 2  |  \n" +
                " 3\n" +
                " 4       X  \n" +
                " 5       |  \n" +
                " 6       |  \n" +
                " 7       |  \n" +
                " 8\n" +
                " 9\n" +
                "10\n" +
                "11\n", model2ToAddBeatsPrint.getMusicState());
        assertEquals(12, model2ToAddBeatsPrint.beatLength());
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
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.B, 4, 4, 0, true));
        this.modelToAddRangePrint.mixMusic(model2ToAddBeatsPrint, 0);
        this.modelToAddRangePrint.trim(0, 4);

        System.out.print(this.modelToAddRangePrint.getMusicState());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullConcat() {
        this.initData();
        this.modelToAddRangePrint.musicConcat(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMix() {
        this.initData();
        this.model2ToAddBeatsPrint.mixMusic(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFromMixToSmall() {
        this.initData();
        this.model2ToAddBeatsPrint.mixMusic(this.modelToAddRangePrint, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFromMixTooLarge() {
        this.initData();
        this.model2ToAddBeatsPrint.mixMusic(this.modelToAddRangePrint, 100);
    }

    @Test
    public void testBasicConcat() {
        this.initData();
        this.model2ToAddBeatsPrint.addNote(0, new Note(Note.Pitch.C, 1, 2, 0, true));
        this.model2ToAddBeatsPrint.addNote(1, new Note(Note.Pitch.D, 1, 2, 1, true));
        this.model2ToAddBeatsPrint.addNote(2, new Note(Note.Pitch.E, 1, 2, 2, true));
        assertEquals("   C2  C♯2   D2  D♯2   E2 \n" +
                "0  X  \n" +
                "1            X  \n" +
                "2                      X  \n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n", this.model2ToAddBeatsPrint.getMusicState());
        this.modelToAddRangePrint.addNote(0, new Note(Note.Pitch.C, 1, 2, 0, true));
        this.modelToAddRangePrint.addNote(1, new Note(Note.Pitch.D, 1, 2, 1, true));
        this.modelToAddRangePrint.addNote(2, new Note(Note.Pitch.E, 1, 2, 2, true));
        assertEquals("   C2  C♯2   D2  D♯2   E2 \n" +
                "0  X  \n" +
                "1            X  \n" +
                "2                      X  \n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n", this.modelToAddRangePrint.getMusicState());
        this.model2ToAddBeatsPrint.musicConcat(this.modelToAddRangePrint);
        assertEquals("    C2  C♯2   D2  D♯2   E2 \n" +
                " 0  X  \n" +
                " 1            X  \n" +
                " 2                      X  \n" +
                " 3\n" +
                " 4\n" +
                " 5\n" +
                " 6\n" +
                " 7\n" +
                " 8  X  \n" +
                " 9            X  \n" +
                "10                      X  \n" +
                "11\n" +
                "12\n" +
                "13\n" +
                "14\n" +
                "15\n" +
                "16\n" +
                "17\n" +
                "18\n" +
                "19\n" +
                "20\n" +
                "21\n" +
                "22\n" +
                "23\n" +
                "24\n" +
                "25\n" +
                "26\n" +
                "27\n", this.model2ToAddBeatsPrint.getMusicState());
    }

    //TODO test a big number of notes
    //TODO test a big number of range
    //TODO trim's exceptions
    //trim's new length
    //trim the whole thing
    //trim one beat




}
