package ru.vtv.hw.practical.detective;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DetectiveCaseTest {
    private static DetectiveCase detectiveCase;

    private static final String FINGERPRINTS = "Отпечатки пальцев";
    private static final String FOOTPRINTS = "Следы обуви";
    private static final String HAIRS = "Волосы";

    private static final String WEAPON = "Оружие";
    private static final String FAKE = "Фальшивое доказательство";
    private static final String UNREAL_FIRST = "не существующее доказательство 1";
    private static final String UNREAL_SECOND = "не существующее доказательство 2";

    @BeforeEach
    void setUp() {
        detectiveCase = new DetectiveCase();
        detectiveCase.addEvidence(FINGERPRINTS);
        detectiveCase.addEvidence(FOOTPRINTS);
        detectiveCase.addEvidence(HAIRS);
    }

    @Test
    void testGetEvidencesReturnsCopy() {
        var evidences = detectiveCase.getEvidences();
        assertThrows(UnsupportedOperationException.class, () -> evidences.add(FAKE));
        assertEquals(3, detectiveCase.getEvidences().size());
    }

    @Test
    void testAddEvidenceSuccess() {
        detectiveCase.addEvidence(WEAPON);
        assertTrue(detectiveCase.getEvidences().contains(WEAPON));
    }

    @Test
    void testAddDuplicateEvidenceThrowsException() {
        var expectedMsg = "DetectiveCase already has evidence '" + FINGERPRINTS + "'!";
        var exception = assertThrows(EvidenceException.class, () -> detectiveCase.addEvidence(FINGERPRINTS));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testAddNullEvidenceThrowsException() {
        var expectedMsg = "Invalid name for evidence: 'null'!";
        var exception = assertThrows(EvidenceException.class, () -> detectiveCase.addEvidence(null));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testAddBlankEvidenceThrowsException() {
        var expectedMsg = "Invalid name for evidence: '   '!";
        var exception = assertThrows(EvidenceException.class, () -> detectiveCase.addEvidence("   "));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testRemoveEvidenceSuccess() {
        detectiveCase.removeEvidence(FOOTPRINTS);
        assertFalse(detectiveCase.getEvidences().contains(FOOTPRINTS));
    }

    @Test
    void testRemoveNullEvidenceThrowsException() {
        var expectedMsg = "Invalid name for evidence: 'null'!";
        var exception = assertThrows(EvidenceException.class, () -> detectiveCase.removeEvidence(null));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testRemoveBlankEvidenceThrowsException() {
        var expectedMsg = "Invalid name for evidence: '   '!";
        var exception = assertThrows(EvidenceException.class, () -> detectiveCase.removeEvidence("   "));
        assertEquals(expectedMsg, exception.getMessage());
    }

    @Test
    void testCheckEvidenceFound() {
        assertTrue(detectiveCase.checkEvidence(FINGERPRINTS));
    }

    @Test
    void testCheckEvidenceNotFound() {
        assertFalse(detectiveCase.checkEvidence(UNREAL_FIRST));
    }

    @Test
    void testCheckEvidenceNullReturnsFalse() {
        assertFalse(detectiveCase.checkEvidence(null));
    }

    @Test
    void testCheckEvidenceBlankReturnsFalse() {
        assertFalse(detectiveCase.checkEvidence("   "));
    }

    @Test
    void testCheckEvidencesSomeFound() {
        var toCheck = Set.of(FINGERPRINTS, FOOTPRINTS, UNREAL_FIRST);
        var result = detectiveCase.checkEvidences(toCheck);
        assertEquals(Set.of(FINGERPRINTS, FOOTPRINTS), result);
    }

    @Test
    void testCheckEvidencesAllFound() {
        var toCheck = Set.of(FINGERPRINTS, FOOTPRINTS, HAIRS);
        var result = detectiveCase.checkEvidences(toCheck);
        assertEquals(toCheck, result);
    }

    @Test
    void testCheckEvidencesNoneFound() {
        var toCheck = Set.of(UNREAL_FIRST, UNREAL_SECOND);
        var result = detectiveCase.checkEvidences(toCheck);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCheckEvidencesEmptySet() {
        var result = detectiveCase.checkEvidences(Set.of());
        assertTrue(result.isEmpty());
    }
}
