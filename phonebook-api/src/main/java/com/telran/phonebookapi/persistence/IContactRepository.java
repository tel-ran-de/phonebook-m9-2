package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.model.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IContactRepository extends CrudRepository<Contact, Integer> {
    public List<Contact> findAll ();
    public List<Contact> findByUserEmail (String email);
    public Contact findById (int id);
    public Contact findByLastName (String lastName);
    public Contact findByPhones (int phoneNumber);

}
