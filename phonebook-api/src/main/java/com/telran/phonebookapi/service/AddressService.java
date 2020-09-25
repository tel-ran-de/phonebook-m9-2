package com.telran.phonebookapi.service;

import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.persistance.IAddressRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AddressService {

    static final String ADDRESS_DOES_NOT_EXIST = "Error! This address doesn't exist";

    IContactRepository contactRepository;
    IAddressRepository addressRepository;
    AddressMapper addressMapper;

    public AddressService(IContactRepository contactRepository,
                          IAddressRepository addressRepository,
                          AddressMapper addressMapper) {
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public void add(int contactId, String zip,
                    String country,
                    String city,
                    String street) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(()
                -> new EntityNotFoundException(ContactService.CONTACT_DOES_NOT_EXIST));

        Address address = new Address(zip, country, city, street, contact);
        addressRepository.save(address);
    }

    public void editAllFields(int id, String newZip,
                              String newCountry,
                              String newCity,
                              String newStreet) {
        Address address = addressRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        address.setCountry(newCountry);
        address.setCity(newCity);
        address.setStreet(newStreet);
        address.setZip(newZip);
        addressRepository.save(address);
    }

    public Address getById(int id) {
        return addressRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
    }

    public void removeById(int id) {
        addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        addressRepository.deleteById(id);
    }

}
