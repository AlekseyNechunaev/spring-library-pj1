package ru.nechunaev.service.implementation;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nechunaev.entity.Person;
import ru.nechunaev.handlers.PersonNotFoundException;
import ru.nechunaev.repository.PersonRepository;
import ru.nechunaev.service.PersonService;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> showAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Person show(Long id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Person showWithBooks(Long id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        Hibernate.initialize(person.getBooks());
        return person;
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public void update(Long id, Person inputPerson) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setFullName(inputPerson.getFullName());
        person.setYearOfBirth(inputPerson.getYearOfBirth());
        personRepository.save(person);
    }

    @Override
    public void remove(Long id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
    }
}
