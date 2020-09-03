package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.mapper.PhoneMapper;
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
    PhoneMapper phoneMapper;

    public PhoneService(IContactRepository contactRepository, IPhoneRepository phoneRepository, PhoneMapper phoneMapper) {
        this.contactRepository = contactRepository;
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
    }

    public void add(PhoneDto phoneDto) {
        Contact contact = contactRepository.findById(phoneDto.contactId).orElseThrow(() -> new EntityNotFoundException(ContactService.CONTACT_DOES_NOT_EXIST));
        Phone phone = new Phone(phoneDto.countryCode, phoneDto.phoneNumber, contact);
        phoneRepository.save(phone);
    }

    public void editAllFields(PhoneDto phoneDto) {
        Phone phone = phoneRepository.findById(phoneDto.contactId).orElseThrow(() -> new EntityNotFoundException(PHONE_DOES_NOT_EXIST));
        phone.setCountryCode(phoneDto.countryCode);
        phone.setPhoneNumber(phoneDto.phoneNumber);
        phoneRepository.save(phone);
    }

    public PhoneDto getById(int id) {
        Phone phone = phoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(PHONE_DOES_NOT_EXIST));
        PhoneDto phoneDto = phoneMapper.mapPhoneToDto(phone);
        return phoneDto;
    }

    public void removeById(int id) {
        phoneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(PHONE_DOES_NOT_EXIST));
        phoneRepository.deleteById(id);
    }

    public List<PhoneDto> getAllPhoneNumbersByContactId(int contactId) {
        return phoneRepository.findAllByContactId(contactId).stream()
                .map(phoneMapper::mapPhoneToDto)
                .collect(Collectors.toList());
    }
}
