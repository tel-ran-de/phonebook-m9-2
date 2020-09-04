package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEmailRepository extends CrudRepository <Email, Integer> {

    List<Email> findAll();

    List<Email> findAllByContactId(int id);
}
