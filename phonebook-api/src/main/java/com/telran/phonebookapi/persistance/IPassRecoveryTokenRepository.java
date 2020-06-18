package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.RecoveryToken;
import org.springframework.data.repository.CrudRepository;

public interface IPassRecoveryTokenRepository extends CrudRepository<RecoveryToken, String> {
}
