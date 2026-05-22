package ru.freelib.model.dto.ai;

import java.util.List;

public record AiEmbeddingResponse(List<Data> data) {
    public record Data(float[] embedding) {}
}