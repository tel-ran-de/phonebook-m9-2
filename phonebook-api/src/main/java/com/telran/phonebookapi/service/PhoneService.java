package com.telran.phonebookapi.service;

import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Phone;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.ICountryCodeRepository;
import com.telran.phonebookapi.persistance.IPhoneRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PhoneService {

    static final String PHONE_DOES_NOT_EXIST = "Error! This phone number doesn't exist";

    IContactRepository contactRepository;
    IPhoneRepository phoneRepository;
    ICountryCodeRepository countryCodeRepository;

    public PhoneService(IContactRepository contactRepository, IPhoneRepository phoneRepository, ICountryCodeRepository countryCodeRepository) {
        this.contactRepository = contactRepository;
        this.phoneRepository = phoneRepository;
        this.countryCodeRepository = countryCodeRepository;
    }

    public void add(int contactId, int countryCode, long number) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new EntityNotFoundException(ContactService.CONTACT_DOES_NOT_EXIST));
        if (countryCodeRepository.findById(countryCode).isPresent()) {
            Phone phone = new Phone(countryCode, number, contact);
            phoneRepository.save(phone);
        } else {
            throw new EntityNotFoundException(CountryCodeService.CODE_DOES_NOT_EXIST);
        }
    }

    public void editAllFields(int id, int countryCode, long number) {
        Phone phone = phoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(PHONE_DOES_NOT_EXIST));
        phone.setCountryCode(countryCode);
        phone.setPhoneNumber(number);
        phoneRepository.save(phone);
    }

    public Phone getById(int id) {
        return phoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(PHONE_DOES_NOT_EXIST));
    }

    public void removeById(int id) {
        phoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(PHONE_DOES_NOT_EXIST));
        phoneRepository.deleteById(id);
    }

}
