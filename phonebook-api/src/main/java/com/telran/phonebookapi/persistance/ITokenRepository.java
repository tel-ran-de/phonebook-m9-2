package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Token;
import org.springframework.data.repository.CrudRepository;

public interface ITokenRepository extends CrudRepository<Token, String> {
}
