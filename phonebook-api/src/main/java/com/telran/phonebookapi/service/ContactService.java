package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.*;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.mapper.ContactMapper;
import com.telran.phonebookapi.mapper.EmailMapper;
import com.telran.phonebookapi.mapper.PhoneMapper;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import com.telran.phonebookapi.persistance.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    static final String CONTACT_DOES_NOT_EXIST = "Error! This contact doesn't exist in our DB";

    IUserRepository userRepository;
    IContactRepository contactRepository;
    IAddressRepository addressRepository;
    IPhoneRepository phoneRepository;
    IEmailRepository emailRepository;
    ContactMapper contactMapper;
    AddressMapper addressMapper;
    PhoneMapper phoneMapper;
    EmailMapper emailMapper;

    public ContactService(IUserRepository userRepository, IContactRepository contactRepository, IAddressRepository addressRepository, IPhoneRepository phoneRepository, IEmailRepository emailRepository, ContactMapper contactMapper, AddressMapper addressMapper, PhoneMapper phoneMapper, EmailMapper emailMapper) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
        this.contactMapper = contactMapper;
        this.addressMapper = addressMapper;
        this.phoneMapper = phoneMapper;
        this.emailMapper = emailMapper;
    }

    public void add(ContactDto contactDto) {
        User user = userRepository.findById(contactDto.userId).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
        Contact contact = new Contact(contactDto.firstName, user);
        contact.setLastName(contactDto.lastName);
        contact.setDescription(contactDto.description);
        contactRepository.save(contact);
    }

    public ContactDto getById(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        return contactMapper.mapContactToDto(contact);
    }

    public ContactDto getByIdFullDetails(int id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        ContactDto contactDto = contactMapper.mapContactToDtoFull(contact, getAllPhonesByContact(contact), getAllAddressesByContact(contact), getAllEmailsByContact(contact));

        contactDto.addresses = contact.getAddresses().stream()
                .map(addressMapper::mapAddressToDto)
                .collect(Collectors.toList());

        contactDto.phoneNumbers = contact.getPhoneNumbers().stream()
                .map(phoneMapper::mapPhoneToDto)
                .collect(Collectors.toList());

        contactDto.emails = contact.getEmails().stream()
                .map(emailMapper::mapEmailToDto)
                .collect(Collectors.toList());

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

    public List<ContactDto> getAllContactsByUserId(UserEmailDto userEmailDto) {
        return contactRepository.findAllByUserEmail(userEmailDto.email).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void addProfile(ContactDto contactDto) {
        User user = userRepository.findById(contactDto.userId).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
        Contact profile = user.getMyProfile();
        profile.setFirstName(contactDto.firstName);
        profile.setLastName(contactDto.lastName);
        profile.setDescription(contactDto.description);
        profile.setUser(user);
        user.addProfile(profile);
        contactRepository.save(profile);
    }

    public void editProfile(ContactDto contactDto) {
        Contact newProfile = contactRepository.findById(contactDto.id).orElseThrow(() -> new EntityNotFoundException(CONTACT_DOES_NOT_EXIST));
        newProfile.setFirstName(contactDto.firstName);
        newProfile.setLastName(contactDto.lastName);
        contactRepository.save(newProfile);
    }

    private List<PhoneDto> getAllPhonesByContact(Contact contact) {
        return phoneRepository.findAllByContactId(contact.getId())
                .stream()
                .map(phoneMapper::mapPhoneToDto)
                .collect(Collectors.toList());
    }

    private List<AddressDto> getAllAddressesByContact(Contact contact) {
        return addressRepository.findAllByContactId(contact.getId())
                .stream()
                .map(addressMapper::mapAddressToDto)
                .collect(Collectors.toList());
    }

    public List<EmailDto> getAllEmailsByContact(Contact contact) {
        return emailRepository.findAllByContactId(contact.getId())
                .stream()
                .map(emailMapper::mapEmailToDto)
                .collect(Collectors.toList());
    }

    private ContactDto convertToDto(Contact contact) {
        return contactMapper.mapContactToDtoFull(contact, getAllPhonesByContact(contact), getAllAddressesByContact(contact), getAllEmailsByContact(contact));
    }

    public ContactDto getProfile(UserEmailDto userEmailDto) {
        User user = userRepository.findById(userEmailDto.email).orElseThrow(() -> new EntityNotFoundException(UserService.USER_DOES_NOT_EXIST));
        Contact contact = user.getMyProfile();
        return contactMapper.mapContactToDtoFull(contact, getAllPhonesByContact(contact), getAllAddressesByContact(contact), getAllEmailsByContact(contact));
    }

}
