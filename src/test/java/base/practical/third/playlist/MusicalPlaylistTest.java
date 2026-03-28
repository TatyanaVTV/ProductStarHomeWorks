package base.practical.third.playlist;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MusicalPlaylistTest {
    private final static String SONG_ONE = "Music 1";
    private final static String SONG_TWO = "Music 2";
    private final static String SONG_THREE = "Music 3";
    private final static String SONG_FOUR = "Music 4";

    @Test
    public void testAddSong() {
        var playlist = getInitialPlaylist();

        assertEquals(0, playlist.getSongs().size());

        playlist.addSong(SONG_ONE);
        assertEquals(1, playlist.getSongs().size());
    }


    @Test
    public void testRemoveSong() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO, SONG_THREE, SONG_FOUR);
        var originalState = playlist.getSongs();

        playlist.removeSong(SONG_TWO);

        assertEquals(4, originalState.size());
        assertEquals(3, playlist.getSongs().size());
        assertFalse(playlist.getSongs().contains(SONG_TWO));
    }

    @Test
    public void testMoveSong_Forward() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO, SONG_THREE, SONG_FOUR);

        playlist.moveSong(1, 3);

        var expectedState = List.of(SONG_ONE, SONG_THREE, SONG_FOUR, SONG_TWO);
        assertEquals(expectedState, playlist.getSongs());
    }

    @Test
    public void testMoveSong_Backward() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO, SONG_THREE, SONG_FOUR);

        playlist.moveSong(3, 1);

        var expectedState = List.of(SONG_ONE, SONG_FOUR, SONG_TWO, SONG_THREE);
        assertEquals(expectedState, playlist.getSongs());
    }

    @Test
    public void testMoveSong_FirstToLast() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO, SONG_THREE, SONG_FOUR);

        playlist.moveSong(0, 3);

        var expectedState = List.of(SONG_TWO, SONG_THREE, SONG_FOUR, SONG_ONE);
        assertEquals(expectedState, playlist.getSongs());
    }

    @Test
    public void testMoveSong_LastToFirst() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO, SONG_THREE, SONG_FOUR);

        playlist.moveSong(3, 0);

        var expectedState = List.of(SONG_FOUR, SONG_ONE, SONG_TWO, SONG_THREE);
        assertEquals(expectedState, playlist.getSongs());
    }

    @Test
    public void testMoveSongSamePosition() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO, SONG_THREE, SONG_FOUR);

        var originalState = playlist.getSongs();
        playlist.moveSong(2, 2);
        assertEquals(originalState, playlist.getSongs());
    }

    @Test
    public void testMoveSong_InvalidPositions_ThrowsException() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO, SONG_THREE, SONG_FOUR);

        var expectedExceptionMessage = "Указаны позиции за пределами текущего плейлиста";
        var playListSize = playlist.getSongs().size();

        SoftAssertions.assertSoftly(softly -> {
            // Проверка 1: отрицательный индекс 'from'
            softly.assertThatThrownBy(() -> playlist.moveSong(-1, 0))
                    .as("Отрицательный индекс 'from' должен вызывать исключение")
                    .isInstanceOf(MusicalPlayListMovingException.class)
                    .hasMessageContaining(expectedExceptionMessage);

            // Проверка 2: отрицательный индекс 'to'
            softly.assertThatThrownBy(() -> playlist.moveSong(0, -1))
                    .as("Отрицательный индекс 'to' должен вызывать исключение")
                    .isInstanceOf(MusicalPlayListMovingException.class)
                    .hasMessageContaining(expectedExceptionMessage);

            // Проверка 3: индекс 'from' выходит за границы (>= size)
            softly.assertThatThrownBy(() -> playlist.moveSong(playListSize, 0))
                    .as("Индекс 'from' >= размера плейлиста должен вызывать исключение")
                    .isInstanceOf(MusicalPlayListMovingException.class)
                    .hasMessageContaining(expectedExceptionMessage);

            // Проверка 4: индекс 'to' выходит за границы (>= size)
            softly.assertThatThrownBy(() -> playlist.moveSong(0, playListSize))
                    .as("Индекс 'to' >= размера плейлиста должен вызывать исключение")
                    .isInstanceOf(MusicalPlayListMovingException.class)
                    .hasMessageContaining(expectedExceptionMessage);

            // Проверка 5: оба индекса выходят за границы
            softly.assertThatThrownBy(() -> playlist.moveSong(playListSize, playListSize))
                    .as("Оба индекса за границами должны вызывать исключение")
                    .isInstanceOf(MusicalPlayListMovingException.class)
                    .hasMessageContaining(expectedExceptionMessage);
        });
    }

    @Test
    public void testShuffle_MultipleSongs_ChangesOrder() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO, SONG_THREE, SONG_FOUR);

        var originalOrder = playlist.getSongs();
        var currentOrder =  playlist.getSongs();

        var orderChanged = false;
        for (int i = 0; i < 10; i++) {
            playlist.shuffle();
            currentOrder =  playlist.getSongs();
            if (!originalOrder.equals(currentOrder)) {
                orderChanged = true;
                break;
            }
        }

        assertTrue(orderChanged, "После 10 запусков shuffle() порядок должен был измениться хотя бы один раз!");

        var sortedOriginal = new ArrayList<>(originalOrder);
        sortedOriginal.sort(String::compareTo);
        var sortedShuffled = new ArrayList<>(currentOrder);
        sortedShuffled.sort(String::compareTo);

        assertEquals(sortedOriginal, sortedShuffled, "Состав плейлиста не должен меняться при меремешивании!");
    }

    @Test
    void testShuffle_EmptyPlaylist_DoesNotChange() {
        var playlist = getInitialPlaylist();

        playlist.shuffle();

        assertTrue(playlist.getSongs().isEmpty(), "Пустой плейлист должен остаться пустым после shuffle()");
    }

    @Test
    void testShuffle_SingleSong_DoesNotChange() {
        var playlist = getInitialPlaylist(SONG_ONE);

        var originalOrder = new ArrayList<>(playlist.getSongs());

        playlist.shuffle();

        assertEquals(originalOrder, playlist.getSongs(), "Плейлист с одной песней не должен измениться после shuffle()");
    }

    @Test
    void testShuffle_TwoSongs_MayChangeOrder() {
        var playlist = getInitialPlaylist(SONG_ONE, SONG_TWO);

        var originalOrder = new ArrayList<>(playlist.getSongs());

        playlist.shuffle();
        var currentOrder = playlist.getSongs();

        // Assert: порядок может остаться тем же (вероятность 50 %), но состав не изменится
        assertEquals(2, currentOrder.size(), "Размер плейлиста должен остаться прежним");
        assertTrue(currentOrder.contains(SONG_ONE), "Песня 'Music 1' должна остаться в плейлисте");
        assertTrue(currentOrder.contains(SONG_TWO), "Песня 'Music 2' должна остаться в плейлисте");

        // Дополнительно: если порядок изменился — это ожидаемо
        if (!originalOrder.equals(currentOrder)) {
            assertNotEquals(originalOrder.getFirst(), currentOrder.getFirst());
        }
    }

    private static MusicPlaylistImpl getInitialPlaylist(String... songs) {
        var initialSongs = List.of(songs);
        var playlist = new MusicPlaylistImpl();
        playlist.addSongs(initialSongs);
        return playlist;
    }
}
