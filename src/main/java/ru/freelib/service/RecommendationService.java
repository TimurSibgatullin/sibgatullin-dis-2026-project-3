package ru.freelib.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.freelib.config.AiClientConfig;
import ru.freelib.exception.AiServiceException;
import ru.freelib.model.dto.ai.AiEmbeddingRequest;
import ru.freelib.model.dto.ai.AiEmbeddingResponse;
import ru.freelib.repository.BookRepository;
import ru.freelib.util.PromptSanitizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final OkHttpClient httpClient;
    private final ObjectMapper mapper;
    private final AiClientConfig aiConfig;
    private final BookRepository bookRepository;

    public float[] generateEmbedding(String title, String author, String description, List<String> genres) {
        String input = String.format("%s | %s | %s | Жанры: %s",
                PromptSanitizer.sanitizeInput(title, 200),
                PromptSanitizer.sanitizeInput(author, 100),
                PromptSanitizer.sanitizeInput(description, 1500),
                PromptSanitizer.sanitizeInput(String.join(", ", genres), 200)
        );

        AiEmbeddingRequest request = new AiEmbeddingRequest(aiConfig.getModelEmbed(), input);

        try {
            String json = mapper.writeValueAsString(request);
            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
            Request httpRequest = new Request.Builder()
                    .url(aiConfig.getBaseUrl() + "/embeddings")
                    .post(body)
                    .build();

            try (Response response = httpClient.newCall(httpRequest).execute()) {
                if (!response.isSuccessful()) {
                    log.error("Embedding API error: {} {}", response.code(), response.message());
                    throw new AiServiceException("Ошибка генерации эмбеддинга: " + response.code());
                }
                AiEmbeddingResponse aiResp = mapper.readValue(response.body().string(), AiEmbeddingResponse.class);
                return aiResp.data().get(0).embedding();
            }
        } catch (IOException e) {
            log.error("Failed to generate embedding", e);
            throw new AiServiceException("Не удалось получить эмбеддинг", e);
        }
    }

    @Transactional
    public void updateBookEmbedding(Long bookId, String title, String author, String description, List<String> genres) {
        float[] vector = generateEmbedding(title, author, description, genres);
        bookRepository.updateEmbeddingVector(bookId, vector);
    }

    public List<Long> findSimilarBookIds(Long excludeBookId, float[] queryVector, int limit) {
        String vectorString = Arrays.toString(queryVector).replace(" ", "");
        return bookRepository.findSimilarIds(excludeBookId, vectorString, limit);
    }
}