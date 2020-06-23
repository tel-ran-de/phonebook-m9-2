package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.ActivationToken;
import org.springframework.data.repository.CrudRepository;

public interface IActivationTokenRepository extends CrudRepository<ActivationToken, String> {
}
