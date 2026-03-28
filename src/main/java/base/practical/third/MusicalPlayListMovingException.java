package base.practical.third;

public class MusicalPlayListMovingException extends IndexOutOfBoundsException {
    public MusicalPlayListMovingException(String message) {
        super(message);
    }

    public static MusicalPlayListMovingException positionOutSideOfPlaylist(int from, int to, int actualSize) {
        return new MusicalPlayListMovingException(
                String.format("Указаны позиции за пределами текущего плейлиста: from = %d, to = %d, текущий размер : %d!",
                        from, to, actualSize)
        );
    }
}
