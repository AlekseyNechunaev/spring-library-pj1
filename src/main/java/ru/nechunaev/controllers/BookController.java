package ru.nechunaev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nechunaev.constant.Paths;
import ru.nechunaev.entity.Book;
import ru.nechunaev.service.BookService;
import ru.nechunaev.service.PersonService;

import javax.validation.Valid;

@Controller
@RequestMapping(Paths.BOOK)
public class BookController {

    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }


    @GetMapping
    public String index(Model model, @RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name = "sort_by_year", required = false) Boolean sortByYear) {
        model.addAttribute("books", bookService.showAll(page, booksPerPage, sortByYear));
        return "book/index";
    }

    @GetMapping(Paths.ID)
    public String show(@PathVariable Long id, Model model) {
        Book book = bookService.show(id);
        model.addAttribute("book", book);
        model.addAttribute("persons", personService.showAll());
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
        bookService.save(book);
        return "redirect:/book";
    }

    @GetMapping(Paths.EDIT)
    public String editBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.show(id));
        return "book/edit";
    }

    @PatchMapping(Paths.ID)
    public String update(@PathVariable Long id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/book/edit";
        }
        bookService.update(id, book);
        return "redirect:/book";
    }

    @GetMapping(Paths.DELETE)
    public String removeBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.show(id));
        return "book/remove";
    }

    @DeleteMapping(Paths.ID)
    public String delete(@PathVariable Long id) {
        bookService.remove(id);
        return "redirect:/book";
    }

    @DeleteMapping(Paths.RELEASE)
    public String release(@PathVariable Long id) {
        bookService.updateOwner(id, null);
        return "redirect:/book/" + id;
    }

    @PostMapping(Paths.APPOINT)
    public String appoint(@PathVariable Long id, @RequestParam Long personId) {
        bookService.updateOwner(id, personId);
        return "redirect:/book/" + id;
    }

    @GetMapping(Paths.SEARCH)
    public String search(@RequestParam(required = false) String searchTitleBook, Model model) {
        if (searchTitleBook != null) {
            model.addAttribute("matchBooks", bookService.searchByNameContains(searchTitleBook));
        }
        return "book/search";
    }

}
