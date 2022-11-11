package ru.nechunaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nechunaev.constant.Paths;
import ru.nechunaev.dao.BookDao;
import ru.nechunaev.dao.PersonDao;
import ru.nechunaev.entity.Person;

import javax.validation.Valid;

@Controller
@RequestMapping(Paths.PERSON)
public class PersonController {

    private final PersonDao personDao;
    private final BookDao bookDao;

    @Autowired
    public PersonController(PersonDao personDao, BookDao bookDao) {
        this.personDao = personDao;
        this.bookDao = bookDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("persons", personDao.showAll());
        return "person/index";
    }

    @GetMapping(Paths.NEW)
    public String newPerson(@ModelAttribute Person person) {
        return "person/new";
    }

    @GetMapping(Paths.ID)
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("person", personDao.show(id));
        model.addAttribute("books", bookDao.showAllBooksByPersonId(id));
        return "person/show";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "person/new";
        }
        personDao.save(person);
        return "redirect:/person";
    }

    @GetMapping(Paths.EDIT)
    public String editPerson(@PathVariable Long id, Model model) {
        model.addAttribute("person", personDao.show(id));
        return "person/edit";
    }

    @PatchMapping(Paths.ID)
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "person/edit";
        }
        personDao.update(id, person);
        return "redirect:/person";
    }

    @GetMapping(Paths.DELETE)
    public String removePerson(@PathVariable Long id, Model model) {
        model.addAttribute("person", personDao.show(id));
        return "person/remove";
    }

    @DeleteMapping(Paths.ID)
    public String delete(@PathVariable Long id) {
        personDao.delete(id);
        return "redirect:/person";
    }
}
