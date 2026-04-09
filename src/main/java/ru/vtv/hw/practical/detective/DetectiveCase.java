package ru.vtv.hw.practical.detective;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static ru.vtv.hw.practical.detective.EvidenceException.alreadyExists;
import static ru.vtv.hw.practical.detective.EvidenceException.invalidName;

public class DetectiveCase {
    private final Set<String> evidences = new HashSet<>();

    public Set<String> getEvidences() {
        return Set.copyOf(evidences);
    }

    public void addEvidence(String evidence) {
        if (evidences.contains(evidence)) throw alreadyExists(evidence);
        if (isNull(evidence) || evidence.isBlank()) throw invalidName(evidence);

        evidences.add(evidence);
    }

    public void removeEvidence(String evidence) {
        if (isNull(evidence) || evidence.isBlank()) throw invalidName(evidence);
        evidences.remove(evidence);
    }

    public boolean checkEvidence(String evidence) {
        if (isNull(evidence) || evidence.isBlank()) return false;
        return evidences.contains(evidence);
    }

    public Set<String> checkEvidences(Set<String> evidencesToCheck) {
        return evidencesToCheck.stream().filter(evidences::contains).collect(Collectors.toSet());
    }
}
