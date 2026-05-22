package ru.freelib.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IdempotencyService {

    private static final String SESSION_KEY = "idempotency_tokens";

    @SuppressWarnings("unchecked")
    public String generateToken(HttpSession session) {
        String token = UUID.randomUUID().toString();
        Set<String> tokens = (Set<String>) session.getAttribute(SESSION_KEY);
        if (tokens == null) {
            tokens = ConcurrentHashMap.newKeySet();
            session.setAttribute(SESSION_KEY, tokens);
        }
        tokens.add(token);
        return token;
    }

    @SuppressWarnings("unchecked")
    public boolean validateAndConsume(HttpSession session, String submittedToken) {
        if (submittedToken == null || submittedToken.isBlank()) {
            return false;
        }
        Set<String> tokens = (Set<String>) session.getAttribute(SESSION_KEY);
        return tokens != null && tokens.remove(submittedToken);
    }
}