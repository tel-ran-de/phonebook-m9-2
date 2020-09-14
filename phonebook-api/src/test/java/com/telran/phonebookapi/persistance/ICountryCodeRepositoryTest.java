package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.CountryCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ICountryCodeRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ICountryCodeRepository countryCodeRepository;

    @Test
    public void testFindAll_oneRecord_found() {

        CountryCode code = new CountryCode(49, "Germany");

        entityManager.persist(code);
        entityManager.flush();
        entityManager.clear();

        List<CountryCode> foundContacts = countryCodeRepository.findAll();
        assertEquals(1, foundContacts.size());

    }

    @Test
    public void testFindByCode_oneRecord_found() {

        CountryCode code = new CountryCode(49, "Germany");

        entityManager.persist(code);
        entityManager.flush();
        entityManager.clear();

        Optional<CountryCode> foundCode = countryCodeRepository.findById(49);
        assertEquals(code.getCode(), foundCode.get().getCode());

    }

}
