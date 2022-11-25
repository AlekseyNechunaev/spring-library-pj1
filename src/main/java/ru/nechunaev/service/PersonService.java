package ru.nechunaev.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nechunaev.entity.Person;
import ru.nechunaev.handlers.PersonNotFoundException;
import ru.nechunaev.repository.PersonRepository;

import java.util.List;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> showAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person show(Long id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Person showWithBooks(Long id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        Hibernate.initialize(person.getBooks());
        return person;
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public void update(Long id, Person inputPerson) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setFullName(inputPerson.getFullName());
        person.setYearOfBirth(inputPerson.getYearOfBirth());
    }

    public void remove(Long id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
    }
}
