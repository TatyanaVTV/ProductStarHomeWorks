package base.practical.third.html;

import org.jetbrains.annotations.NotNull;

public record ValidationResult(boolean isValid, String errorMessage) {
    @Override
    public @NotNull String toString() {
        return isValid ? "Valid HTML" : "Invalid HTML: " + errorMessage;
    }
}
