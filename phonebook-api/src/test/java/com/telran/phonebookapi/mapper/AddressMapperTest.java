package com.telran.phonebookapi.mapper;

import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.model.Address;
import com.telran.phonebookapi.model.Contact;
import com.telran.phonebookapi.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    AddressMapper addressMapper = new AddressMapper();

    @Test
    void AddressDto() {
        User user = new User("test@gmail.com", "112233");
        Contact contact = new Contact("Name", user);
        Address address = new Address("10000", "Germany", "Berlin", "Strasse 100", contact);

        AddressDto addressDto = new AddressDto(1, "10000", "Germany", "Berlin", "Strasse 100", 1);

        AddressDto addressDtoMapped = addressMapper.mapAddressToDto(address);
        assertEquals(addressDto.zip, addressDtoMapped.zip);
        assertEquals(addressDto.country, addressDtoMapped.country);
        assertEquals(addressDto.city, addressDtoMapped.city);
        assertEquals(addressDto.street, addressDtoMapped.street);
    }
}
