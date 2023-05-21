package com.tigerhall.repository;

import com.tigerhall.entity.Sighting;
import com.tigerhall.entity.Tiger;
import com.tigerhall.respository.SightingRepository;
import com.tigerhall.respository.TigerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class SightingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SightingRepository sightingRepository;

    @Autowired
    private TigerRepository tigerRepository;

    @Test
    public void testFindByTiger_IdOrderByTimestampDesc() {
        // given
        Tiger tiger = new Tiger();
        tiger.setName("Tiger1");
        tiger.setDateOfBirth(LocalDate.of(2020, 1, 1));
        tiger.setLastSeenTimestamp(LocalDateTime.now().minusDays(1));
        tiger.setLastSeenLatitude(12.34);
        tiger.setLastSeenLongitude(56.78);
        tiger = entityManager.persistAndFlush(tiger);

        Sighting sighting1 = new Sighting();
        sighting1.setTiger(tiger);
        sighting1.setTimestamp(LocalDateTime.now().minusHours(1));
        sighting1.setLatitude(12.34);
        sighting1.setLongitude(56.78);
        sighting1 = entityManager.persistAndFlush(sighting1);

        Sighting sighting2 = new Sighting();
        sighting2.setTiger(tiger);
        sighting2.setTimestamp(LocalDateTime.now());
        sighting2.setLatitude(12.34);
        sighting2.setLongitude(56.78);
        sighting2 = entityManager.persistAndFlush(sighting2);

        // when
        Page<Sighting> result = sightingRepository.findByTiger_IdOrderByTimestampDesc(tiger.getId(), PageRequest.of(0, 10));

        // then
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals(sighting2.getId(), result.getContent().get(0).getId());
        Assertions.assertEquals(sighting1.getId(), result.getContent().get(1).getId());

    }

    @Test
    public void testFindTopByTigerOrderByTimestampDesc() {
        // given
        Tiger tiger = new Tiger();
        tiger.setName("Tiger1");
        tiger.setDateOfBirth(LocalDate.of(2020, 1, 1));
        tiger.setLastSeenTimestamp(LocalDateTime.now().minusDays(1));
        tiger.setLastSeenLatitude(12.34);
        tiger.setLastSeenLongitude(56.78);
        tiger = entityManager.persistAndFlush(tiger);

        Sighting sighting1 = new Sighting();
        sighting1.setTiger(tiger);
        sighting1.setTimestamp(LocalDateTime.now().minusHours(1));
        sighting1.setLatitude(12.34);
        sighting1.setLongitude(56.78);
        sighting1 = entityManager.persistAndFlush(sighting1);

        Sighting sighting2 = new Sighting();
        sighting2.setTiger(tiger);
        sighting2.setTimestamp(LocalDateTime.now());
        sighting2.setLatitude(12.34);
        sighting2.setLongitude(56.78);
        sighting2 = entityManager.persistAndFlush(sighting2);

        // when
        Optional<Sighting> result = sightingRepository.findTopByTigerOrderByTimestampDesc(tiger);

        // then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(sighting2.getId(), result.get().getId());
    }
}
