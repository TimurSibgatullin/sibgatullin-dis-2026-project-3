package ru.freelib.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.freelib.service.AiDescriptionService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiApiController {

    private final AiDescriptionService aiDescriptionService;

    @PostMapping("/generate-description")
    public ResponseEntity<Map<String, String>> generateDescription(@RequestBody Map<String, Object> payload) {
        try {
            String title = (String) payload.get("title");
            String author = (String) payload.get("author");
            @SuppressWarnings("unchecked")
            List<String> genres = (List<String>) payload.get("genres");

            String description = aiDescriptionService.generateDescription(title, author, genres);
            return ResponseEntity.ok(Map.of("description", description));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/improve-description")
    public ResponseEntity<Map<String, String>> improveDescription(@RequestBody Map<String, Object> payload) {
        try {
            String existingDesc = (String) payload.get("existingDesc");
            String title = (String) payload.get("title");
            String author = (String) payload.get("author");
            @SuppressWarnings("unchecked")
            List<String> genres = (List<String>) payload.get("genres");

            String description = aiDescriptionService.improveDescription(existingDesc, title, author, genres);
            return ResponseEntity.ok(Map.of("description", description));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}