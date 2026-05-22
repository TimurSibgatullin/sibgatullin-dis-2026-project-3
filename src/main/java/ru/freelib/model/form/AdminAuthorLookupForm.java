package ru.freelib.model.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminAuthorLookupForm {
    @NotBlank(message = "Введите никнейм автора для поиска")
    private String authorNickname;
}