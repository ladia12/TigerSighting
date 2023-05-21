package com.tigerhall.service;

import com.tigerhall.entity.Sighting;
import com.tigerhall.entity.Tiger;
import com.tigerhall.respository.SightingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SightingServiceTest {

    @Mock
    private SightingRepository sightingRepository;

    @InjectMocks
    private SightingService sightingService;

    private Tiger tiger;
    private Sighting sighting;

    @BeforeEach
    public void setup(){
        tiger = new Tiger();
        tiger.setId(1L);
        tiger.setName("Test Tiger");

        sighting = new Sighting();
        sighting.setId(1L);
        sighting.setTiger(tiger);
        sighting.setTimestamp(LocalDateTime.now());
        sighting.setLatitude(42.2);
        sighting.setLongitude(9.1);
    }

    @Test
    public void testSaveSightingSuccess() {
        when(sightingRepository.findTopByTigerOrderByTimestampDesc(any(Tiger.class))).thenReturn(Optional.empty());
        when(sightingRepository.save(any(Sighting.class))).thenReturn(sighting);

        Sighting savedSighting = sightingService.saveSighting(sighting);

        assertNotNull(savedSighting);
        assertEquals(sighting.getId(), savedSighting.getId());
    }

    // TODO: Add more tests for saveSighting method considering different cases

    @Test
    public void testFindAllSightingsByTigerId() {
        Pageable pageable = PageRequest.of(0, 5);
        when(sightingRepository.findByTiger_IdOrderByTimestampDesc(anyLong(), any(Pageable.class)))
                .thenReturn(Page.empty());

        Page<Sighting> sightingPage = sightingService.findAllSightingsByTigerId(1L, pageable);

        assertNotNull(sightingPage);
    }
}

