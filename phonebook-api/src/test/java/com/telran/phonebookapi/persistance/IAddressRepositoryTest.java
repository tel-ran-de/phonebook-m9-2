package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IAddressRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IAddressRepository addressRepository;

    @Test
    public void testFindByContact_oneRecord_found() {

        User ivan = new User("ivan@gmail.com", "12345");
        Contact mama = new Contact("Mama", ivan);
        Address mamaAddress = new Address(mama);

        entityManager.persist(ivan);
        entityManager.persist(mama);
        entityManager.persist(mamaAddress);
        mama.addAddress(mamaAddress);

        mamaAddress.setCountry("Russia");
        mamaAddress.setCity("SPb");
        mamaAddress.setStreet("Nevskiy pr., 23- 19");
        mamaAddress.setZip("190000");

        entityManager.flush();
        entityManager.clear();
        List<Address> foundAddresses = addressRepository.findAll();
        assertEquals(1, foundAddresses.size());

        assertEquals("Nevskiy pr., 23- 19", foundAddresses.get(0).getStreet());
        assertEquals("Russia", foundAddresses.get(0).getCountry());
        assertEquals("SPb", foundAddresses.get(0).getCity());
        assertEquals("190000", foundAddresses.get(0).getZip());

    }

    @Test
    public void testFindAll_noAddressesExist_emptyList() {
        List<Address> foundAddresses = addressRepository.findAll();
        assertEquals(0, foundAddresses.size());
    }
}


