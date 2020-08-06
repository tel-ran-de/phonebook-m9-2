package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IContactRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findAll();

    List<Contact> findByFirstName(String name);

    List<Contact> getByUser(User user);

}
