package ru.vtv.hw.practical.cybersport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerRatingTest {
    private RankingSystem rankingSystem;
    private static final Player ALICE = new Player(1, "Alice");
    private static final Player BOB = new Player(2, "Bob");

    private static final String PLAYER_ALREADY_RANKED_MSG = "Player %s is already ranked!";
    private static final String PLAYER_NOT_RANKED_MSG = "Player with ID = %d isn't ranked yet!";

    @BeforeEach
    void setUp() {
        rankingSystem = new RankingSystem();
    }

    @Test
    void addPlayer_newPlayer_addsSuccessfully() {
        assertDoesNotThrow(() -> rankingSystem.addPlayer(ALICE));
        assertEquals(0, rankingSystem.getPlayerRank(1));
    }

    @Test
    void addPlayer_alreadyRankedPlayer_throwsException() {
        rankingSystem.addPlayer(ALICE);
        var exception = assertThrows(RankingSystemException.class, () -> rankingSystem.addPlayer(ALICE));
        assertEquals(format(PLAYER_ALREADY_RANKED_MSG, ALICE), exception.getMessage());
    }

    @Test
    void updatePlayerRating_existingPlayer_updatesSuccessfully() {
        rankingSystem.addPlayer(ALICE);
        assertDoesNotThrow(() -> rankingSystem.updatePlayerRating(1, 100));
        assertEquals(100, rankingSystem.getPlayerRank(1));
    }

    @Test
    void updatePlayerRating_nonExistentPlayer_throwsPlayerNotRankedException() {
        var exception = assertThrows(RankingSystemException.class,
                () -> rankingSystem.updatePlayerRating(999, 100)
        );
        assertEquals(format(PLAYER_NOT_RANKED_MSG, 999), exception.getMessage());
    }

    @Test
    void getTopPlayers_nZero_returnsEmptyList() {
        var topPlayers = rankingSystem.getTopPlayers(0);
        assertTrue(topPlayers.isEmpty());
    }

    @Test
    void getTopPlayers_withPlayers_returnsTopNPlayers() {
        rankingSystem.addPlayer(ALICE);
        rankingSystem.addPlayer(BOB);
        rankingSystem.updatePlayerRating(1, 200);
        rankingSystem.updatePlayerRating(2, 150);

        var topPlayers = rankingSystem.getTopPlayers(2);
        assertEquals(2, topPlayers.size());
        assertEquals(ALICE.getName(), topPlayers.get(0).getName());
        assertEquals(BOB.getName(), topPlayers.get(1).getName());
    }

    @Test
    void getPlayerRank_existingPlayer_returnsCorrectRank() {
        rankingSystem.addPlayer(ALICE);
        rankingSystem.updatePlayerRating(1, 150);
        int rank = rankingSystem.getPlayerRank(1);
        assertEquals(150, rank);
    }

    @Test
    void getPlayerRank_nonExistentPlayer_throwsPlayerNotRankedException() {
        var exception = assertThrows(RankingSystemException.class, () -> rankingSystem.getPlayerRank(999));
        assertEquals(format(PLAYER_NOT_RANKED_MSG, 999), exception.getMessage());
    }

    @Test
    void testEquals_sameObject_returnsTrue() {
        assertTrue(ALICE.equals(ALICE));
    }

    @Test
    void testEquals_differentObjectsWithSameIdAndName_returnsTrue() {
        var aliceCopy = new Player(ALICE.getId(), ALICE.getName());
        assertEquals(ALICE, aliceCopy);
    }

    @Test
    void testEquals_differentIds_returnsFalse() {
        assertNotEquals(ALICE, BOB);
    }

    @Test
    void testEquals_nullObject_returnsFalse() {
        assertFalse(ALICE.equals(null));
    }

    @Test
    void testEquals_otherClass_returnsFalse() {
        assertNotEquals(ALICE, rankingSystem);
    }

    @Test
    void testHashCode_sameIdAndName_sameHashCode() {
        var aliceCopy = new Player(ALICE.getId(), ALICE.getName());
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
    }

    @Test
    void testCompareTo_higherRating_returnsNegative() {
        ALICE.setRating(100);
        BOB.setRating(50);
        assertTrue(BOB.compareTo(ALICE) < 0);
    }

    @Test
    void testToString_returnsExpectedFormat() {
        assertEquals(format("%s (id = %d)", ALICE.getName(), ALICE.getId()), ALICE.toString());
    }
}
