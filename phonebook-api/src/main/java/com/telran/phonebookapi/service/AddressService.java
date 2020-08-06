package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.persistance.IAddressRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AddressService {

    static final String ADDRESS_DOES_NOT_EXIST = "Error! This address doesn't exist in our DB";

    IContactRepository contactRepository;
    IAddressRepository addressRepository;
    AddressMapper addressMapper;

    public AddressService(IContactRepository contactRepository, IAddressRepository addressRepository, AddressMapper addressMapper) {
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public void add(AddressDto addressDto) {
        Contact contact = contactRepository.findById(addressDto.contactId).orElseThrow(() -> new EntityNotFoundException(ContactService.CONTACT_DOES_NOT_EXIST));
        Address address = new Address(addressDto.zip, addressDto.country, addressDto.city, addressDto.street, contact);
        addressRepository.save(address);
    }

    public void editAllFields(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.id).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        address.setCountry(addressDto.country);
        address.setCity(addressDto.city);
        address.setStreet(addressDto.street);
        address.setZip(addressDto.zip);
        addressRepository.save(address);
    }

    public AddressDto getById(int id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        AddressDto addressDto = addressMapper.mapAddressToDto(address);
        return addressDto;
    }

    public void removeById(int id) {
        addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        addressRepository.deleteById(id);
    }
}
