package base.practical.third;

import java.util.List;public interface MusicPlaylist {
    List<String> getSongs();
    void addSongs(List<String> titles);
    void addSong(String title);
    void removeSong(String title);
    void moveSong(int from, int to);
    void shuffle();
}
