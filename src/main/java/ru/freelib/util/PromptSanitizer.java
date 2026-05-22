package ru.freelib.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class PromptSanitizer {

    private static final Pattern DANGEROUS_UNICODE = Pattern.compile(
            "[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F\\u200B-\\u200F\\u202A-\\u202E\\u00AD]"
    );

    private static final Pattern INJECTION_PHRASES = Pattern.compile(
            "(?i)(" +
                    "ignore\\s+(all\\s+)?previous|забудь\\s+(все\\s+)?предыдущие|игнорируй\\s+инструкци|" +
                    "system\\s*:|система\\s*:|assistant\\s*:|ассистент\\s*:|user\\s*:|пользователь\\s*:|" +
                    "override|перезапиши|disregard|не\\s+обращай\\s+внимания|" +
                    "new\\s+instruction|новые\\s+указания|выполни\\s+следующее|act\\s+as|действуй\\s+как|" +
                    "<\\|.*?\\|>|\\[SYSTEM\\]|\\[INST\\]|\\[CMD\\]|prompt\\s*injection" +
                    ")"
    );

    public static String sanitizeInput(String input, int maxLength) {
        if (input == null || input.isBlank()) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFC);
        String cleaned = DANGEROUS_UNICODE.matcher(normalized).replaceAll("");
        cleaned = INJECTION_PHRASES.matcher(cleaned).replaceAll("[filtered]");
        cleaned = cleaned.trim().replaceAll("\\s+", " ");
        return cleaned.length() > maxLength ? cleaned.substring(0, maxLength) : cleaned;
    }

    public static String sanitizeOutput(String output, int maxLength) {
        if (output == null) return "";
        String cleaned = output.replaceAll("[*_~#`>|\\[\\]{}]", "");
        cleaned = cleaned.replaceAll("<[^>]+>", "");
        cleaned = cleaned.replaceAll("(?i)(system:|assistant:|ignore|override|забудь|перезапиши)", "");
        cleaned = cleaned.trim().replaceAll("\\s+", " ");
        return cleaned.length() > maxLength ? cleaned.substring(0, maxLength) : cleaned;
    }
}