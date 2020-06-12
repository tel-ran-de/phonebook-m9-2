package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, String> {

    List<User> findAll();

}
