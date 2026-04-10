package ru.vtv.hw.practical.cybersport;

import static java.lang.String.format;

public class RankingSystemException extends RuntimeException {
    private RankingSystemException(String message) {
        super(message);
    }

    public static RankingSystemException playerAlreadyRanked(Player player) {
        return new RankingSystemException(format("Player %s is already ranked!", player));
    }

    public static RankingSystemException playerNotRanked(int playerId) {
        return new RankingSystemException(format("Player with ID = %d isn't ranked yet!", playerId));
    }
}
