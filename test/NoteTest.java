import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bahar on 6/8/16.
 */
public class NoteTest {
  Note C = new Note(Note.Pitch.C, 4, 2, 0, true);
  Note Ccopy = new Note(Note.Pitch.C, 4, 2, 0, true);
  Note F = new Note(Note.Pitch.F, 4, 2, 0, true);
  Note Fhigh = new Note(Note.Pitch.F, 4,9, 0, true);

  @Test
  public void testRenderBasicNote() {
    assertEquals("  X  \n" +
            "  |  \n" +
            "  |  \n" +
            "  |  \n", C.getNoteState());
    assertEquals("C2", C.toString());
    assertEquals(F.getNoteState(), C.getNoteState());
    assertNotEquals(F.toString(), C.toString());
  }

  //testing constructor exceptions
  @Test(expected = IllegalArgumentException.class)
  public void testDurationExceptionNegative() {
    Note error = new Note(Note.Pitch.B, -1, 2, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDurationExceptionTooBig() {
    Note error = new Note(Note.Pitch.B, 5, 2, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOctaveExceptionNegative() {
    Note error = new Note(Note.Pitch.B, 3, -1, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOctaveExceptionTooBig() {
    Note error = new Note(Note.Pitch.B, 3, 10, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBeatException() {
    Note error = new Note(Note.Pitch.B, 3, 5, -1, true);
  }

  @Test
  public void testEquals() {
    assertEquals(this.C, this.C);
    assertEquals(this.Ccopy, this.C);
    assertNotEquals(this.C, this.F);
  }

  @Test
  public void testCompare() {
    assertEquals(0, C.compareTo(Ccopy));
    assertTrue(C.compareTo(F) < 0);
    assertTrue(F.compareTo(C) > 0);
    assertTrue(F.compareTo(Fhigh) < 0);
    assertTrue(Fhigh.compareTo(F) > 0);
    assertTrue(C.compareTo(Fhigh) < 0);
  }




}