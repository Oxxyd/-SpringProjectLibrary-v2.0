package com.example.Lib.services;


import com.example.Lib.model.Book;
import com.example.Lib.model.Person;
import com.example.Lib.repositories.BooksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAllSort(){
        List<Book> books = booksRepository.findAll();
        Collections.sort(books, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o2.getYear() - o1.getYear();
            }
        });
        return books;
    }

    public Book findOne(int id){
        Optional<Book> book = booksRepository.findById(id);

        return book.orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public Person bookAccess(int id){
        Optional<Book> book = booksRepository.findById(id);

        if(book.isPresent())
            return book.get().getOwner();
        else
            return null;
    }

    @Transactional
    public void makeFree(int id){
        Book book = booksRepository.findById(id).get();

        book.setOwner(null);
        book.setDateOfTaking(null);
    }

    @Transactional
    public void setAPerson(int id, Person person){
        Book book = booksRepository.findById(id).get();

        book.setOwner(person);
        book.setDateOfTaking(new Date());
    }

    public Integer searchReq(Book book){
        String name = book.getName();
        Optional<Book> foundedBook = booksRepository.findByNameStartingWith(name);

        if(foundedBook.isPresent()){
            return foundedBook.get().getId();
        }
        else
            return null;
    }
}
