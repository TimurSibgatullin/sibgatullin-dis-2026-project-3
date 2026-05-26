package ru.freelib.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.freelib.exception.NotFoundException;
import ru.freelib.model.entity.Author;
import ru.freelib.service.AuthorService;

@Component
@RequiredArgsConstructor
public class StringToAuthorConverter implements Converter<String, Author> {

    private final AuthorService authorService;

    @Override
    public Author convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }

        Author author = authorService.findByNickname(source.trim());
        if (author == null) {
            throw new NotFoundException("Автор", source);
        }

        return author;
    }
}