package ru.freelib.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.freelib.model.entity.Author;
import ru.freelib.model.form.AuthorForm;
import ru.freelib.repository.AuthorRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Cacheable(value = "authors", key = "#id")
    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Автор не найден: " + id));
    }

    @Cacheable(value = "authors", key = "'all'")
    public List<Author> findAll() {
        return authorRepository.findAll(Sort.by(Sort.Direction.ASC, "nickname"));
    }

    @Transactional
    @CacheEvict(value = "authors", key = "'all'")
    public Author create(AuthorForm form) {
        if (authorRepository.existsByNickname(form.getNickname())) {
            throw new IllegalArgumentException("Автор с таким никнеймом уже существует");
        }
        Author author = Author.builder()
                .nickname(form.getNickname().trim())
                .bio(form.getBio() != null ? form.getBio().trim() : null)
                .build();
        return authorRepository.save(author);
    }

    @Transactional
    @CachePut(value = "authors", key = "#id")
    @CacheEvict(value = "authors", key = "'all'")
    public Author update(Long id, AuthorForm form) {
        Author author = getById(id);
        author.setNickname(form.getNickname().trim());
        author.setBio(form.getBio() != null ? form.getBio().trim() : null);
        return authorRepository.save(author);
    }

    @Transactional
    @CacheEvict(value = "authors", allEntries = true)
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Автор не найден для удаления: " + id);
        }
        authorRepository.deleteById(id);
    }

    public Author findByNickname(String nickname) {
        return authorRepository.findByNickname(nickname).orElse(null);
    }
}