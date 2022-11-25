package ru.nechunaev.service;

import ru.nechunaev.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> showAll(Integer page, Integer booksPerPage, Boolean sortByYear);

    Book show(Long id);

    void save(Book book);

    void update(Long id, Book inputBook);

    void remove(Long id);

    void updateOwner(Long id, Long personId);

    boolean isExpired(Book book);

    List<Book> searchByNameContains(String name);
}
