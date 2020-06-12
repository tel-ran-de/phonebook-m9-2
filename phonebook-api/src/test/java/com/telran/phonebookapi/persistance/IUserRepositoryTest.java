package com.telran.phonebookapi.persistance;

import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IUserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IUserRepository userRepository;

    @Test
    public void testFindByEmail_oneRecord_found() {
        User ivan = new User("ivan@gmail.com", "12345");

        entityManager.persist(ivan);

        entityManager.flush();
        entityManager.clear();
        List<User> foundUsers = userRepository.findAll();
        assertEquals(1, foundUsers.size());

        Optional<User> foundUser = userRepository.findById("ivan@gmail.com");
        assertEquals("12345", foundUser.get().getPassword());

    }

    @Test
    public void testFindAll_noUsersExist_emptyList() {
        List<User> foundUsers = userRepository.findAll();
        assertEquals(0, foundUsers.size());
    }
}

