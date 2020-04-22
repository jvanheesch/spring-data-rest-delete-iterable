package com.github.jvanheesch;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends org.springframework.data.repository.Repository<Book, Long> {
    // Needed because of DefaultExposureAwareCrudMethods: Lazy.of(() -> exposes(crudMethods.getDeleteMethod()) && crudMethods.hasFindOneMethod());
    Optional<Book> findById(Long id);

    Book save(Book entity);

    default void delete(Iterable<? extends Book> authors) {
        // pretend this is properly implemented
    }
}
