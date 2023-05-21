package com.tigerhall.repository;

import com.tigerhall.entity.Tiger;
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

@DataJpaTest
public class TigerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TigerRepository tigerRepository;

    @Test
    public void testFindAllTigersOrderByLastSeenTimestampDesc() {
        // given
        Tiger tiger1 = new Tiger();
        tiger1.setName("Tiger1");
        tiger1.setDateOfBirth(LocalDate.of(2020, 1, 1));
        tiger1.setLastSeenTimestamp(LocalDateTime.now().minusDays(1));
        tiger1.setLastSeenLatitude(12.34);
        tiger1.setLastSeenLongitude(56.78);
        entityManager.persistAndFlush(tiger1);

        Tiger tiger2 = new Tiger();
        tiger2.setName("Tiger2");
        tiger2.setDateOfBirth(LocalDate.of(2020, 1, 1));
        tiger2.setLastSeenTimestamp(LocalDateTime.now());
        tiger2.setLastSeenLatitude(12.34);
        tiger2.setLastSeenLongitude(56.78);
        entityManager.persistAndFlush(tiger2);

        // when
        Page<Tiger> result = tigerRepository.findAllTigersOrderByLastSeenTimestampDesc(PageRequest.of(0, 10));

        // then
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals(tiger2.getName(), result.getContent().get(0).getName());
        Assertions.assertEquals(tiger1.getName(), result.getContent().get(1).getName());
    }
}
