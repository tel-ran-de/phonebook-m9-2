package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Email;
import com.telran.phonebookapi.model.Phone;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class IEmailRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IEmailRepository emailRepository;

    @Test
    public void testFindAll_oneRecord_found() {

        User ivan = new User("ivan@gmail.com", "12345");
        Contact mama = new Contact("Mama", ivan);
        Email mamaEmail = new Email(mama);

        entityManager.persist(ivan);
        entityManager.persist(mama);
        entityManager.persist(mamaEmail);

        mama.addEmail(mamaEmail);
        mamaEmail.setEmail("mail@mail.com");

        entityManager.flush();
        entityManager.clear();

        List<Email> foundEmails = emailRepository.findAll();
        assertEquals(1, foundEmails.size());

        assertEquals("mail@mail.com", foundEmails.get(0).getEmail());

    }

    @Test
    public void testFindAll_noPhonesExist_emptyList() {
        List<Email> foundEmails = emailRepository.findAll();
        assertEquals(0, foundEmails.size());
    }
}
