package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserRepository extends CrudRepository<User, Integer> {
    public List<User> findAll();
    public User findByEmail(String email);



}
