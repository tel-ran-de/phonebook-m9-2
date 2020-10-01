package com.telran.phonebookapi.model;

import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.ICountryCodeRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class TesterUser implements CommandLineRunner {

    IUserRepository userRepository;
    IContactRepository contactRepository;
    ICountryCodeRepository countryCodeRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${com.telran.tester.user.email}")
    String email;
    @Value("${com.telran.tester.user.password}")
    String password;

    public TesterUser(IUserRepository userRepository, 
                      IContactRepository contactRepository, 
                      ICountryCodeRepository countryCodeRepository, 
                      BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.countryCodeRepository = countryCodeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findById(email).isEmpty()) {
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            User testUser = new User(email, encodedPassword);
            testUser.addRole(UserRole.USER);
            testUser.setActive(true);
            Contact profile = new Contact();
            profile.setDescription("tester profile");
            testUser.setMyProfile(profile);
            contactRepository.save(profile);
            userRepository.save(testUser);
            profile.setUser(testUser);
            contactRepository.save(profile);

            CountryCode codeFR = new CountryCode(33, "France");
            CountryCode codeES = new CountryCode(34, "Spain");
            CountryCode codeIT = new CountryCode(39, "Italy");
            CountryCode codeUK = new CountryCode(44, "England");
            CountryCode codeDE = new CountryCode(49, "Germany");

            countryCodeRepository.save(codeFR);
            countryCodeRepository.save(codeES);
            countryCodeRepository.save(codeIT);
            countryCodeRepository.save(codeUK);
            countryCodeRepository.save(codeDE);
        }
    }
}
