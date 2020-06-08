package com.telran.phonebookapi.persistence;

import com.telran.phonebookapi.model.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPhonePerository extends CrudRepository<Phone, Integer> {
    public List<Phone> findAll ();
    public List<Phone> findAllByCountryCode (int countryCode);

}
