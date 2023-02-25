package com.example.Lib.services;


import com.example.Lib.model.Book;
import com.example.Lib.model.Person;
import com.example.Lib.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundedPerson = peopleRepository.findById(id);

        return foundedPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> booksAvailability(int id){
        Optional<Person> person = peopleRepository.findById(id);

        if(person.isPresent()){
            return person.get().getBooks();
        }
        else
            return Collections.emptyList();
    }

    public List<Integer> overdueBooks(List<Book> list){
        List<Integer> overdueBooksId = new ArrayList<>();
        Date curDate = new Date();
        for(Book book: list){
            Date bookTakenDate = book.getDateOfTaking();
            long diff = curDate.getTime() - bookTakenDate.getTime();
            long millsecInTenDays =  10 * 24 * 60 * 60 * 1000;
            if(diff >= millsecInTenDays)
                overdueBooksId.add(book.getId());
        }

        return overdueBooksId;
    }

}
