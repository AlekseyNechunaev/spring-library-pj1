package ru.nechunaev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nechunaev.constant.Paths;
import ru.nechunaev.entity.Person;
import ru.nechunaev.service.PersonService;

import javax.validation.Valid;

@Controller
@RequestMapping(Paths.PERSON)
public class PersonController {


    private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("persons", personService.showAll());
        return "person/index";
    }

    @GetMapping(Paths.NEW)
    public String newPerson(@ModelAttribute Person person) {
        return "person/new";
    }

    @GetMapping(Paths.ID)
    public String show(@PathVariable Long id, Model model) {
        Person person = personService.showWithBooks(id);
        model.addAttribute("person", person);
        return "person/show";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "person/new";
        }
        personService.save(person);
        return "redirect:/person";
    }

    @GetMapping(Paths.EDIT)
    public String editPerson(@PathVariable Long id, Model model) {
        model.addAttribute("person", personService.show(id));
        return "person/edit";
    }

    @PatchMapping(Paths.ID)
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "person/edit";
        }
        personService.update(id, person);
        return "redirect:/person";
    }

    @GetMapping(Paths.DELETE)
    public String removePerson(@PathVariable Long id, Model model) {
        model.addAttribute("person", personService.show(id));
        return "person/remove";
    }

    @DeleteMapping(Paths.ID)
    public String delete(@PathVariable Long id) {
        personService.remove(id);
        return "redirect:/person";
    }
}
