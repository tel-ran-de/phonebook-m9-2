package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.CountryCodeDto;
import com.telran.phonebookapi.model.CountryCode;
import com.telran.phonebookapi.persistance.ICountryCodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryCodeServiceTest {

    @Mock
    ICountryCodeRepository countryCodeRepository;

    @InjectMocks
    CountryCodeService countryCodeService;

    @Test
    public void testAdd_newCountryCode_countryCodeAdded() {
        CountryCode code = new CountryCode("+49", "Germany");
        CountryCodeDto countryCodeDto = new CountryCodeDto(0, "+49", "Germany");
        countryCodeService.add(countryCodeDto.id, countryCodeDto.code, countryCodeDto.country);

        verify(countryCodeRepository, times(1)).save(any());

    }

    @Test
    public void testEditAllFields_codeExists_AllFieldsChanged() {

        CountryCode code = new CountryCode("+49", "Germany");
        int id = 1;
        when(countryCodeRepository.findById(id)).thenReturn(Optional.of(code));
        CountryCodeDto countryCodeDto = new CountryCodeDto(1, "+39", "Italy");

        countryCodeService.editAllFields(id, countryCodeDto.code, countryCodeDto.country);

        verify(countryCodeRepository, times(1)).save(any());
        verify(countryCodeRepository, times(1)).save(argThat(countryCode ->
                countryCode.getCode() == countryCodeDto.code && countryCode.getCountry() == countryCodeDto.country)
        );
    }

    @Test
    public void testEditAny_codeDoesNotExist_EntityNotFoundException() {

        CountryCodeDto countryCodeDto = new CountryCodeDto(1, "+39", "Italy");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> countryCodeService.editAllFields(countryCodeDto.id, countryCodeDto.code, countryCodeDto.country));

        verify(countryCodeRepository, times(1)).findById(any());
        assertEquals("Error! This country code doesn't exist in our DB", exception.getMessage());
    }

    @Captor
    ArgumentCaptor<CountryCode> countryCodeArgumentCaptor;

    @Test
    public void testRemoveById_codeExists_CodeDeleted() {

        CountryCode code = new CountryCode("+49", "Germany");
        int id = 1;
        when(countryCodeRepository.findById(id)).thenReturn(Optional.of(code));
        CountryCodeDto countryCodeDto = new CountryCodeDto(1, "+39", "Italy");

        countryCodeService.removeById(id);

        List<CountryCode> capturedCodes = countryCodeArgumentCaptor.getAllValues();
        verify(countryCodeRepository, times(1)).deleteById(1);
        assertEquals(0, capturedCodes.size());
    }

    @Test
    public void testGetById_oneRecord_Code() {

        CountryCode code = new CountryCode("+49", "Germany");
        when(countryCodeRepository.findById(1)).thenReturn(Optional.of(code));
        CountryCodeDto countryCodeDto = new CountryCodeDto(1, "+49", "Germany");
        CountryCode codeFound = countryCodeService.getById(1);

        assertEquals(countryCodeDto.code, codeFound.getCode());
        assertEquals(countryCodeDto.country, codeFound.getCountry());
    }

    @Test
    public void testGetAllCodes_twoCodes_codesList() {

        CountryCode code01 = new CountryCode("+49", "Germany");
        CountryCode code02 = new CountryCode("+39", "Italy");
        when(countryCodeRepository.findAll()).thenReturn(Arrays.asList(code01, code02));
        CountryCodeDto countryCodeDto01 = new CountryCodeDto(1, "+49", "Germany");
        CountryCodeDto countryCodeDto02 = new CountryCodeDto(2, "+39", "Italy");
        List<CountryCode> codesFound = countryCodeService.getAllCountryCodes();
        verify(countryCodeRepository, times(1)).findAll();
        assertEquals(2, codesFound.size());
        assertEquals(codesFound.get(0).getCode(), countryCodeDto01.code);
        assertEquals(codesFound.get(1).getCountry(), countryCodeDto02.country);
    }

}
