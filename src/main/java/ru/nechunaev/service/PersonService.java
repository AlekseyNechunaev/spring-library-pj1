package ru.nechunaev.service;

import ru.nechunaev.entity.Person;

import java.util.List;

public interface PersonService {

    List<Person> showAll();

    Person show(Long id);

    Person showWithBooks(Long id);

    void save(Person person);

    void update(Long id, Person inputPerson);

    void remove(Long id);
}
