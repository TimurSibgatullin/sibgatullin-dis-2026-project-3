package ru.freelib.repository;

import ru.freelib.model.entity.Book;
import java.util.List;

public interface BookRepositoryCustom {
    List<Book> findByDynamicFilters(String titleFragment, Long authorId, List<Long> genreIds, Long minViews, Long maxViews);
}