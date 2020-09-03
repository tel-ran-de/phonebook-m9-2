package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPhoneRepository extends CrudRepository<Phone, Integer> {

    List<Phone> findAll();

    List<Phone> findAllByContactId(int id);

}
