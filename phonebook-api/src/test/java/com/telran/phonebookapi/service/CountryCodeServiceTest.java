package com.telran.phonebookapi.service;

import com.telran.phonebookapi.dto.CountryCodeDto;
import com.telran.phonebookapi.model.CountryCode;
import com.telran.phonebookapi.persistance.ICountryCodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryCodeServiceTest {

    @Mock
    ICountryCodeRepository countryCodeRepository;

    @InjectMocks
    CountryCodeService countryCodeService;

    @Test
    public void testAdd_newCountryCode_countryCodeAdded() {
        CountryCodeDto countryCodeDto = new CountryCodeDto(49, "Germany");
        countryCodeService.add(countryCodeDto.code, countryCodeDto.country);

        verify(countryCodeRepository, times(1)).save(any());

    }

    @Captor
    ArgumentCaptor<CountryCode> countryCodeArgumentCaptor;

    @Test
    public void testRemoveById_codeExists_CodeDeleted() {

        CountryCode code = new CountryCode(49, "Germany");
        when(countryCodeRepository.findById(code.getCode())).thenReturn(Optional.of(code));
        CountryCodeDto countryCodeDto = new CountryCodeDto(49, "Germany");

        countryCodeService.removeById(countryCodeDto.code);

        List<CountryCode> capturedCodes = countryCodeArgumentCaptor.getAllValues();
        verify(countryCodeRepository, times(1)).deleteById(1);
        assertEquals(0, capturedCodes.size());
    }

    @Test
    public void testGetById_oneRecord_Code() {

        CountryCode code = new CountryCode(49, "Germany");
        when(countryCodeRepository.findById(1)).thenReturn(Optional.of(code));
        CountryCodeDto countryCodeDto = new CountryCodeDto(49, "Germany");
        CountryCode codeFound = countryCodeService.getById(countryCodeDto.code);

        assertEquals(countryCodeDto.code, codeFound.getCode());
        assertEquals(countryCodeDto.country, codeFound.getCountry());
    }

    @Test
    public void testGetAllCodes_twoCodes_codesList() {

        CountryCode code01 = new CountryCode(49, "Germany");
        CountryCode code02 = new CountryCode(39, "Italy");
        when(countryCodeRepository.findAll()).thenReturn(Arrays.asList(code01, code02));
        CountryCodeDto countryCodeDto01 = new CountryCodeDto(49, "Germany");
        CountryCodeDto countryCodeDto02 = new CountryCodeDto(39, "Italy");
        List<CountryCode> codesFound = countryCodeService.getAllCountryCodes();
        verify(countryCodeRepository, times(1)).findAll();
        assertEquals(2, codesFound.size());
        assertEquals(codesFound.get(0).getCode(), countryCodeDto01.code);
        assertEquals(codesFound.get(1).getCountry(), countryCodeDto02.country);
    }

}
