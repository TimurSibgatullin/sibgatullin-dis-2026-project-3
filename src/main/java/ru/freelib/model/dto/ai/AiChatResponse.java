package ru.freelib.model.dto.ai;

import java.util.List;

public record AiChatResponse(List<Choice> choices) {
    public record Choice(Message message) {}
    public record Message(String content) {}
}