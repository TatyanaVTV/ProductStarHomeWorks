package ru.vtv.hw.practical.detective;

public class EvidenceException extends RuntimeException {
    private EvidenceException(String message) {
        super(message);
    }

    public static EvidenceException invalidName(String evidence) {
        return new EvidenceException("Invalid name for evidence: '" + evidence + "'!");
    }

    public static EvidenceException alreadyExists(String evidence) {
        return new EvidenceException("DetectiveCase already has evidence '" + evidence + "'!");
    }
}
