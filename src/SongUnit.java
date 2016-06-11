/**
 * Created by baharsheikhi on 6/10/16.
 */
public interface SongUnit<K> {

    /**
     * Adds itself to the end of the given model
     * @param model
     */
    void addToMusic(MusicEditorModel<K> model, int start);
}
