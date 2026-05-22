package ru.freelib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.freelib.model.entity.Book;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    @Query("SELECT b FROM Book b WHERE b.views > (SELECT AVG(b2.views) FROM Book b2)")
    List<Book> findBooksWithViewsAboveAverage();

    List<Book> findByAuthorId(Long authorId);

    @Modifying
    @Query("UPDATE Book b SET b.embeddingVector = :vector WHERE b.id = :id")
    void updateEmbeddingVector(@Param("id") Long id, @Param("vector") float[] vector);

    @Query(value = """
            SELECT id FROM books
            WHERE id != :excludeId AND embedding_vector IS NOT NULL
            ORDER BY embedding_vector <=> CAST(:vector AS vector)
            LIMIT :limit
            """, nativeQuery = true)
    List<Long> findSimilarIds(@Param("excludeId") Long excludeId,
                              @Param("vector") String vector, // Теперь это String
                              @Param("limit") int limit);
}