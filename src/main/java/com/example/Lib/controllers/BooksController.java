package com.example.Lib.controllers;


import com.example.Lib.model.Book;
import com.example.Lib.model.Person;
import com.example.Lib.services.BooksService;
import com.example.Lib.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService){
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "sort", required = false) boolean sort){
        if(sort){
            model.addAttribute("books", booksService.findAllSort());
        }
        else
            model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("book") Book book){
        return "books/search";
    }

    @GetMapping("/searchFor")
    public String searchFor(@ModelAttribute Book book, Model model){
        Integer bookId = booksService.searchReq(book);
        model.addAttribute("bookId", bookId);
        if(bookId != null)
            return "redirect:/books/" + bookId;
        return "books/searchNotFounded";
    }

    @GetMapping("/{id}")
    public String show(Model model, @ModelAttribute("selectedPerson") Person person, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("person", booksService.bookAccess(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/makeFree")
    public String makeFree(@PathVariable("id") int id){
        booksService.makeFree(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/add")
    public String setAPerson(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        booksService.setAPerson(id, person);
        return "redirect:/books/{id}";
    }
}
