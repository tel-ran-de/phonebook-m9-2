package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Phone;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IPhoneRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IPhoneRepository phoneRepository;

    @Test
    public void testFindByName_oneRecord_found() {

        User ivan = new User("ivan@gmail.com", "12345");
        Contact mama = new Contact("Mama", ivan);
        Phone mamaPhone = new Phone(mama);

        entityManager.persist(ivan);
        entityManager.persist(mama);
        entityManager.persist(mamaPhone);

        mama.addPhone(mamaPhone);
        mamaPhone.setCountryCode("7");
        mamaPhone.setPhoneNumber("634872");

        entityManager.flush();
        entityManager.clear();

        List<Phone> foundPhones = phoneRepository.findAll();
        assertEquals(1, foundPhones.size());

        assertEquals("7", foundPhones.get(0).getCountryCode());
        assertEquals("634872", foundPhones.get(0).getPhoneNumber());

    }

    @Test
    public void testFindAll_noPhonesExist_emptyList() {
        List<Phone> foundPhones = phoneRepository.findAll();
        assertEquals(0, foundPhones.size());
    }
}




