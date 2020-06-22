package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.RecoveryToken;
import org.springframework.data.repository.CrudRepository;

public interface IRecoveryTokenRepository extends CrudRepository<RecoveryToken, String> {
}
