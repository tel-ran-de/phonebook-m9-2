package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IContactRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IContactRepository contactRepository;

    @Test
    public void testFindByFirstName_oneRecord_found() {

        User ivan = new User("ivan@gmail.com", "12345");
        Contact mama = new Contact("Mama", ivan);

        entityManager.persist(ivan);
        entityManager.persist(mama);

        mama.addEmail("mama@gmail.com");

        entityManager.flush();
        entityManager.clear();
        List<Contact> foundContacts = contactRepository.findByFirstName("Mama");
        assertEquals(1, foundContacts.size());
        assertEquals("Mama", foundContacts.get(0).getFirstName());

        List<String> foundEmails = foundContacts.get(0).getEmails();
        assertEquals(1, foundEmails.size());

        assertEquals("mama@gmail.com", foundEmails.get(0));

    }

    @Test
    public void testFindAll_noContactsExist_emptyList() {
        List<Contact> foundContacts = contactRepository.findAll();
        assertEquals(0, foundContacts.size());
    }
}
