package ru.freelib.model.form;

import lombok.Data;
import java.util.List;

@Data
public class BookFilterForm {
    private String title;
    private Long authorId;
    private List<Long> genreIds;
    private Long minViews;
    private Long maxViews;
}