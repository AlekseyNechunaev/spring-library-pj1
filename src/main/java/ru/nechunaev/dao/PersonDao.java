package ru.nechunaev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nechunaev.entity.Person;

import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(Long id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findFirst().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (fullName, year_of_birth) VALUES (?, ?)", person.getFullName(),
                person.getYearOfBirth());
    }

    public void update(Long id, Person person) {
        jdbcTemplate.update("UPDATE person SET fullName = ?, year_of_birth = ? WHERE id = ?",
                person.getFullName(), person.getYearOfBirth(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
}
