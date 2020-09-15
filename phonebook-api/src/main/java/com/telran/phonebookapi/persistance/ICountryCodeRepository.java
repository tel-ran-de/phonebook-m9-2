package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.CountryCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICountryCodeRepository extends CrudRepository<CountryCode, Integer> {

    List<CountryCode> findAll();

    CountryCode findByCode(String code);

}
