package ru.freelib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.freelib.model.entity.Author;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
}
