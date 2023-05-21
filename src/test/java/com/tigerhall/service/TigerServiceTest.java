package com.tigerhall.service;

import com.tigerhall.entity.Tiger;
import com.tigerhall.respository.TigerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TigerServiceTest {

    @Mock
    private TigerRepository tigerRepository;

    @InjectMocks
    private TigerService tigerService;

    private Tiger tiger;

    @BeforeEach
    public void setup(){
        tiger = new Tiger();
        tiger.setId(1L);
        tiger.setName("Test Tiger");
        tiger.setDateOfBirth(LocalDate.now());
        tiger.setLastSeenTimestamp(LocalDateTime.now());
        tiger.setLastSeenLatitude(42.2);
        tiger.setLastSeenLongitude(9.1);
    }

    @Test
    public void testFindTigerById() {
        when(tigerRepository.findById(1L)).thenReturn(Optional.of(tiger));

        Tiger foundTiger = tigerService.findTigerById(1L).orElse(null);

        assertNotNull(foundTiger);
        assertEquals(tiger.getId(), foundTiger.getId());
    }

    @Test
    public void testFindAllTigers() {
        Pageable pageable = PageRequest.of(0, 5);
        when(tigerRepository.findAllTigersOrderByLastSeenTimestampDesc(pageable))
                .thenReturn(Page.empty());

        Page<Tiger> tigerPage = tigerService.findAllTigers(pageable);

        assertNotNull(tigerPage);
    }

    @Test
    public void testSaveTigerSuccess() {
        when(tigerRepository.save(any(Tiger.class))).thenReturn(tiger);

        Tiger savedTiger = tigerService.saveTiger(tiger);

        assertNotNull(savedTiger);
        assertEquals(tiger.getId(), savedTiger.getId());
    }

    @Test
    public void givenTigerWithNullName_whenSaveTiger_thenThrowIllegalArgumentException() {
        tiger.setName(null);
        assertThrows(IllegalArgumentException.class, () -> tigerService.saveTiger(tiger));
    }

    @Test
    public void givenTigerWithNullDateOfBirth_whenSaveTiger_thenThrowIllegalArgumentException() {
        tiger.setDateOfBirth(null);
        assertThrows(IllegalArgumentException.class, () -> tigerService.saveTiger(tiger));
    }

    @Test
    public void givenTigerWithNullLastSeenCoordinates_whenSaveTiger_thenThrowIllegalArgumentException() {
        tiger.setLastSeenLatitude(null);
        tiger.setLastSeenLongitude(null);
        assertThrows(IllegalArgumentException.class, () -> tigerService.saveTiger(tiger));
    }

    @Test
    public void givenTigerViolatesConstraints_whenSaveTiger_thenThrowDataIntegrityViolationException() {
        when(tigerRepository.save(any(Tiger.class))).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(DataIntegrityViolationException.class, () -> tigerService.saveTiger(tiger));
    }
}
