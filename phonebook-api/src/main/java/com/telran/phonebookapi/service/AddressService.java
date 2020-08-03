package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.mapper.AddressMapper;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.persistance.IAddressRepository;
import com.telran.phonebookapi.persistance.IContactRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
        Address address = new Address(contact);
        addressRepository.save(address);
    }

    public void editCountry(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.contactId).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        address.setCountry(addressDto.country);
        addressRepository.save(address);
    }

    public void editCity(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.contactId).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        address.setCity(addressDto.city);
        addressRepository.save(address);
    }

    public void editStreet(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.contactId).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        address.setStreet(addressDto.street);
        addressRepository.save(address);
    }

    public void editZip(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.contactId).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        address.setZip(addressDto.zip);
        addressRepository.save(address);
    }


    public AddressDto getById(int id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        AddressDto addressDto = addressMapper.mapAddressToDto(address);
        return addressDto;
    }

    public List<AddressDto> getByContactId(int contactId) {
        return addressRepository.findById(contactId).stream()
                .map(addressMapper::mapAddressToDto)
                .collect(Collectors.toList());
    }

    public void removeById(int id) {
        addressRepository.deleteById(id);
    }
}
