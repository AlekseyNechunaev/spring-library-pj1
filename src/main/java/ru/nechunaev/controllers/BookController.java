package ru.nechunaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nechunaev.constant.Paths;
import ru.nechunaev.dao.BookDao;
import ru.nechunaev.dao.PersonDao;
import ru.nechunaev.entity.Book;

import javax.validation.Valid;

@Controller
@RequestMapping(Paths.BOOK)
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDao.showAll());
        return "book/index";
    }

    @GetMapping(Paths.ID)
    public String show(@PathVariable Long id, Model model) {
        Book book = bookDao.show(id);
        model.addAttribute("book", book);
        model.addAttribute("owner", personDao.show(book.getPersonId()));
        model.addAttribute("persons", personDao.showAll());
        return "book/show";
    }

    @GetMapping(Paths.NEW)
    public String newBook(@ModelAttribute Book book) {
        return "book/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/new";
        }
        bookDao.create(book);
        return "redirect:/book";
    }

    @GetMapping(Paths.EDIT)
    public String editBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "book/edit";
    }

    @PatchMapping(Paths.ID)
    public String update(@PathVariable Long id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/book/edit";
        }
        bookDao.update(id, book);
        return "redirect:/book";
    }

    @GetMapping(Paths.DELETE)
    public String removeBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "book/remove";
    }

    @DeleteMapping(Paths.ID)
    public String delete(@PathVariable Long id) {
        bookDao.delete(id);
        return "redirect:/book";
    }

    @DeleteMapping(Paths.RELEASE)
    public String release(@PathVariable Long id) {
        bookDao.updateOwner(id, null);
        return "redirect:/book/" + id;
    }

    @PostMapping(Paths.APPOINT)
    public String appoint(@PathVariable Long id, @RequestParam Long personId) {
        bookDao.updateOwner(id, personId);
        return "redirect:/book/" + id;
    }
}
