package com.tigerhall.respository;

import com.tigerhall.entity.Sighting;
import com.tigerhall.entity.Tiger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SightingRepository extends JpaRepository<Sighting, Long> {
    Page<Sighting> findByTiger_IdOrderByTimestampDesc(Long tigerId, Pageable pageable);

    Optional<Sighting> findTopByTigerOrderByTimestampDesc(Tiger tiger);
}
