package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.AddressDto;
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

    public AddressService(IContactRepository contactRepository, IAddressRepository addressRepository) {
        this.contactRepository = contactRepository;
        this.addressRepository = addressRepository;
    }

    public void add(AddressDto addressDto) {
        Contact contact = contactRepository.findById(addressDto.contactId).orElseThrow(() -> new EntityNotFoundException(ContactService.CONTACT_DOES_NOT_EXIST));
        Address address = new Address(contact);
        addressRepository.save(address);
    }

    public void editCity(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.contactId).orElseThrow(() -> new EntityNotFoundException(ADDRESS_DOES_NOT_EXIST));
        address.setCity(addressDto.city);
        addressRepository.save(address);
    }

    public List<AddressDto> getByContactId(int contactId) {
        List<Address> addresses = addressRepository.findAllById(contactId);
        List<AddressDto> addressDtos = addresses.stream().map(address -> {
            AddressDto addressDto = new AddressDto();
            addressDto.contactId = address.getContact().getId();
            addressDto.zip = address.getZip();
            addressDto.country = address.getCountry();
            addressDto.city = address.getCity();
            addressDto.street = address.getStreet();
            return addressDto;
        }).collect(Collectors.toList());
        return addressDtos;
    }

    public void removeById(int id) {
        addressRepository.deleteById(id);
    }
}
