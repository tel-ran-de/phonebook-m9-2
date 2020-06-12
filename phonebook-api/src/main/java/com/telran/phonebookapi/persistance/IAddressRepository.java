package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAddressRepository extends CrudRepository<Address, Integer> {

    List<Address> findAll();

}
