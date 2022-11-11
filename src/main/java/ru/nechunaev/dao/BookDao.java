package ru.nechunaev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nechunaev.entity.Book;

import java.util.List;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> showAllBooksByPersonId(Long personId) {
        return jdbcTemplate.query("SELECT * FROM book where person_id = ?",
                new BeanPropertyRowMapper<>(Book.class), personId);
    }

    public Book show(Long id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE ID = ?", new BeanPropertyRowMapper<>(Book.class), id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO book (name, author, year) values (?, ?, ?)",
                book.getName(),
                book.getAuthor(),
                book.getYear());
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM book where id = ?", id);
    }

    public void update(Long id, Book book) {
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, year = ? WHERE id = ?",
                book.getName(),
                book.getAuthor(),
                book.getYear(),
                id);
    }

    public void updateOwner(Long id, Long personID) {
        jdbcTemplate.update("UPDATE  book SET person_id = ? WHERE id = ?", personID, id);
    }


}
