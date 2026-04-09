package ru.vtv.hw.practical.cybersport;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.cybersport.RankingSystemException.*;

@Slf4j
public class RankingSystem {
    private final TreeMap<Integer, TreeSet<Player>> playerRankings = new TreeMap<>();

    public void addPlayer(Player player) {
        var alreadyRanked = playerRankings.values().stream()
                .anyMatch(ranking -> ranking.contains(player));
        if (alreadyRanked) throw playerAlreadyRanked(player);

        player.setRating(0);
        playerRankings.putIfAbsent(0, new TreeSet<>());
        playerRankings.get(0).add(player);

        log.debug("Added player ranking {}", player);
    }

    public void updatePlayerRating(int playerId, int newRating) {
        var playerRank = playerRankings.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .filter(player -> player.getId() == playerId)
                        .map(player -> new AbstractMap.SimpleEntry<>(entry.getKey(), player)))
                .findFirst()
                .orElse(null);
        if (isNull(playerRank)) throw playerNotRanked(playerId);

        var currentRating = playerRank.getKey();
        var player = playerRank.getValue();

        player.setRating(newRating);
        playerRankings.putIfAbsent(newRating, new TreeSet<>());
        playerRankings.get(newRating).add(player);
        playerRankings.get(currentRating).remove(player);

        log.debug("Ranking for player {} has been updated. Old rating = {}, new rating = {}",
                player, currentRating, newRating);
    }

    public List<Player> getTopPlayers(int n) {
        if (n <= 0) return List.of();

        return playerRankings.descendingMap().entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .limit(n)
                .collect(Collectors.toList());
    }

    public int getPlayerRank(int playerId) {
        var playerRatingOpt = getPlayerRankOpt(playerId);
        if (playerRatingOpt.isEmpty()) throw playerNotRanked(playerId);

        return playerRatingOpt.get();
    }

    private Optional<Integer> getPlayerRankOpt(int playerId) {
        return playerRankings.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .anyMatch(player -> player.getId() == playerId))
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
