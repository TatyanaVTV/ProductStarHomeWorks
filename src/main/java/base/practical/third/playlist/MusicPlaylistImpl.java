package base.practical.third.playlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static base.practical.third.playlist.MusicalPlayListMovingException.positionOutSideOfPlaylist;

public class MusicPlaylistImpl implements MusicPlaylist {
    private final ArrayList<String> playlist = new ArrayList<>();

    @Override
    public List<String> getSongs() {
        return List.copyOf(playlist);
    }

    @Override
    public void addSongs(List<String> titles) {
        playlist.addAll(titles);
    }

    @Override
    public void addSong(String title) {
        playlist.add(title);
    }

    @Override
    public void removeSong(String title) {
        playlist.remove(title);
    }

    @Override
    public void moveSong(int from, int to) {
        if (from < 0 || to < 0 || from >= playlist.size() || to >= playlist.size()) {
            throw positionOutSideOfPlaylist(from, to, playlist.size());
        }
        if (from == to) return;

        var movedSong = playlist.remove(from);
        playlist.add(to, movedSong);
    }

    @Override
    public void shuffle() {
        Collections.shuffle(playlist);
    }
}
