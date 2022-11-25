package ru.nechunaev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nechunaev.entity.Book;
import ru.nechunaev.entity.Person;
import ru.nechunaev.handlers.BookNotFoundException;
import ru.nechunaev.handlers.PersonNotFoundException;
import ru.nechunaev.repository.BookRepository;
import ru.nechunaev.repository.PersonRepository;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> showAll(Integer page, Integer booksPerPage, Boolean sortByYear) {
        if ((page != null && booksPerPage != null) && (sortByYear == null || !sortByYear)) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
        if (page != null && booksPerPage != null) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        if (sortByYear != null && sortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        }
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book show(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        boolean isExpired = isExpired(book);
        book.setIsExpired(isExpired);
        return book;
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void update(Long id, Book inputBook) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setAuthor(inputBook.getAuthor());
        book.setName(inputBook.getName());
        book.setYear(inputBook.getYear());
        bookRepository.save(book);
    }

    public void remove(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    public void updateOwner(Long id, Long personId) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        Person person = personId != null ? personRepository.findById(personId).orElseThrow(PersonNotFoundException::new) : null;
        book.setPerson(person);
        bookRepository.save(book);

    }

    public boolean isExpired(Book book) {
        LocalDate now = LocalDate.now();
        if (book.getReservationDate() == null) {
            return false;
        }
        LocalDate reserveDate = book.getReservationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int diffDays = Period.between(reserveDate, now).getDays();
        return diffDays >= 10;
    }

    public List<Book> searchByNameContains(String name) {
        return bookRepository.findAllByNameStartsWith(name);
    }

}
