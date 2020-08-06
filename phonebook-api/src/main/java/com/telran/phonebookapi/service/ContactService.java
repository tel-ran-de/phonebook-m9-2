package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.mapper.ContactMapper;
import com.telran.phonebookapi.mapper.PhoneMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.IAddressRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
import com.telran.phonebookapi.persistance.IPhoneRepository;
import com.telran.phonebookapi.persistance.IUserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
public class ContactService {

    static final String CONTACT_DOES_NOT_EXIST = "Error! This contact doesn't exist in our DB";

    IUserRepository userRepository;
    IContactRepository contactRepository;
    IAddressRepository addressRepository;
    IPhoneRepository phoneRepository;
    ContactMapper contactMapper;
    AddressMapper addressMapper;
    PhoneMapper phoneMapper;

    public ContactService(IUserRepository userRepository, IContactRepository contactRepository, IAddressRepository addressRepository, IPhoneRepository phoneRepository, ContactMapper contactMapper, AddressMapper addressMapper, PhoneMapper phoneMapper) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
        this.contactMapper = contactMapper;
        this.addressMapper = addressMapper;
        this.phoneMapper = phoneMapper;
    }

    public void add(ContactDto contactDto) {
        User user = userRepository.findById(contactDto.userId).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
        Contact contact = new Contact(contactDto.firstName, user);
        contactRepository.save(contact);
    }

    public ContactDto getById(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        ContactDto contactDto = contactMapper.mapContactToDto(contact);
        return contactDto;
    }

    public ContactDto getByIdFullDetails(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        ContactDto contactDto = contactMapper.mapContactToDto(contact);

        contactDto.addresses = contact.getAddresses().stream()
                .map(addressMapper::mapAddressToDto)
                .collect(Collectors.toList());

        contactDto.phoneNumbers = contact.getPhoneNumbers().stream()
                .map(phoneMapper::mapPhoneToDto)
                .collect(Collectors.toList());

        contactDto.emails = contact.getEmails();

        return contactDto;
    }

    public void editAllFields(ContactDto contactDto) {
        Contact contact = contactRepository.findById(contactDto.id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        contact.setFirstName(contactDto.firstName);
        contact.setLastName(contactDto.lastName);
        contact.setDescription(contactDto.description);
        contactRepository.save(contact);
    }

    public void removeById(int id) {
        contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        contactRepository.deleteById(id);
    }
}
