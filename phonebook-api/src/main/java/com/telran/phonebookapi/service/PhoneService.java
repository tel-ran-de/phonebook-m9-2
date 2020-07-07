package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.Phone;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IPhoneRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneService {

    static final String PHONE_DOES_NOT_EXIST = "Error! This phone number doesn't exist in our DB";

    IContactRepository contactRepository;
    IPhoneRepository phoneRepository;

    public PhoneService(IContactRepository contactRepository, IPhoneRepository phoneRepository) {
        this.contactRepository = contactRepository;
        this.phoneRepository = phoneRepository;
    }

    public void add(PhoneDto phoneDto) {
        Contact contact = contactRepository.findById(phoneDto.contactId).orElseThrow(() -> new EntityNotFoundException(ContactService.CONTACT_DOES_NOT_EXIST));
        Phone phone = new Phone(contact);
        phoneRepository.save(phone);

    }

    public void edit(PhoneDto phoneDto) {
        Phone phone = phoneRepository.findById(phoneDto.contactId).orElseThrow(() -> new EntityNotFoundException(PHONE_DOES_NOT_EXIST));
        phone.setPhoneNumber(phoneDto.phoneNumber);
        phoneRepository.save(phone);
    }

    public List<PhoneDto> getByContactId(int contactId) {
        List<Phone> phoneNumbers = phoneRepository.findAllById(contactId);
        List<PhoneDto> phoneNumbersDtos = phoneNumbers.stream().map(phoneNumber -> {
            PhoneDto phoneDto = new PhoneDto();
            phoneDto.contactId = phoneNumber.getContact().getId();
            phoneDto.countryCode = phoneNumber.getCountryCode();
            phoneDto.phoneNumber = phoneNumber.getPhoneNumber();
            return phoneDto;
        }).collect(Collectors.toList());
        return phoneNumbersDtos;
    }

    public void removeById(int id) {
        phoneRepository.deleteById(id);
    }

}
