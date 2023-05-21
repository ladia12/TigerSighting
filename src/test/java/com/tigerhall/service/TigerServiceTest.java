package com.tigerhall.service;

import com.tigerhall.entity.Tiger;
import com.tigerhall.respository.TigerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

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
        tiger.setName("Tiger1");
        tiger.setDateOfBirth(LocalDate.now());
        tiger.setLastSeenTimestamp(LocalDateTime.now());
        tiger.setLastSeenLatitude(50.0);
        tiger.setLastSeenLongitude(50.0);
    }

    @DisplayName("Test findById Success")
    @Test
    public void testFindByIdSuccess() {
        given(tigerRepository.findById(1L)).willReturn(Optional.of(tiger));

        Optional<Tiger> foundTiger = tigerService.findTigerById(1L);

        assertThat(foundTiger).isNotEmpty();
    }

}
