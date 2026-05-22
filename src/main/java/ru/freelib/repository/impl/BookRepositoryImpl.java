package ru.freelib.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.freelib.model.entity.Book;
import ru.freelib.model.entity.Genre;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findByDynamicFilters(String titleFragment, Long authorId, List<Long> genreIds, Long minViews, Long maxViews) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> book = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        if (titleFragment != null && !titleFragment.isBlank()) {
            predicates.add(cb.like(cb.lower(book.get("title")), "%" + titleFragment.toLowerCase() + "%"));
        }

        if (authorId != null) {
            predicates.add(cb.equal(book.get("author").get("id"), authorId));
        }

        if (genreIds != null && !genreIds.isEmpty()) {
            Join<Book, Genre> genreJoin = book.join("genres", JoinType.INNER);
            predicates.add(genreJoin.get("id").in(genreIds));
        }

        if (minViews != null) {
            predicates.add(cb.greaterThanOrEqualTo(book.get("views"), minViews));
        }
        if (maxViews != null) {
            predicates.add(cb.lessThanOrEqualTo(book.get("views"), maxViews));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(book.get("createdAt")));
        cq.distinct(true);

        return em.createQuery(cq).getResultList();
    }
}