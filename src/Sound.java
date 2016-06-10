/**
 * Created by baharsheikhi on 6/10/16.
 */
public abstract class Sound {

    public abstract String getNoteState();

    protected final int duration;

    public Sound(int duration) {
        if (duration < 0 || duration > 4) {
            throw new IllegalArgumentException("Please enter a valid duration");
        }
        this.duration = duration;
    }

    protected int getDuration() {
        return this.duration;
    }
}
