package ru.freelib.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record AiDescriptionRequest(
        @NotBlank String title,
        @NotBlank String author,
        @NotEmpty List<String> genres
) {}