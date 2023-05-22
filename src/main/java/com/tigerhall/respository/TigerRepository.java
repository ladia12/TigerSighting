package com.tigerhall.respository;

import com.tigerhall.entity.Tiger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TigerRepository extends JpaRepository<Tiger, Long> {
    @Query("SELECT t FROM Tiger t ORDER BY t.lastSeenTimestamp DESC")
    Page<Tiger> findAllTigersOrderByLastSeenTimestampDesc(Pageable pageable);
}
