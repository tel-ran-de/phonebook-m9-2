package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserRepository  extends CrudRepository<User, Integer> {

    List<User> findAll();
    User findByEmail(String email);
}
