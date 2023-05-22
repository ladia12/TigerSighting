package com.tigerhall.service;

import com.tigerhall.entity.Tiger;
import com.tigerhall.respository.TigerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TigerService {
    private final TigerRepository tigerRepository;

    public TigerService(TigerRepository tigerRepository) {
        this.tigerRepository = tigerRepository;
    }

    /**
     * Retrieves a tiger by its ID.
     *
     * @param id The ID of the tiger
     * @return The optional tiger object
     */
    public Optional<Tiger> findTigerById(Long id) {
        return tigerRepository.findById(id);
    }

    /**
     * Retrieves a paginated list of all tigers.
     *
     * @param pageable The pageable object specifying the page number and size
     * @return A page of tiger objects
     */
    public Page<Tiger> findAllTigers(Pageable pageable) {
        return tigerRepository.findAllTigersOrderByLastSeenTimestampDesc(pageable);
    }

    /**
     * Saves a tiger.
     *
     * @param tiger The tiger object to be saved
     * @return The saved tiger object
     * @throws IllegalArgumentException        if the tiger data is invalid
     * @throws DataIntegrityViolationException if there is a data integrity violation
     */
    public Tiger saveTiger(Tiger tiger) throws IllegalArgumentException, DataIntegrityViolationException {
        if (tiger.getName() == null || tiger.getName().isEmpty()) {
            throw new IllegalArgumentException("Tiger name cannot be empty or null");
        }

        if (tiger.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of Birth cannot be null");
        }

        if (tiger.getLastSeenTimestamp() == null) {
            tiger.setLastSeenTimestamp(LocalDateTime.now());
        }

        if (tiger.getLastSeenLatitude() == null || tiger.getLastSeenLongitude() == null) {
            throw new IllegalArgumentException("Last Seen Coordinates cannot be empty or null");
        }

        try {
            return tigerRepository.save(tiger);
        } catch (DataIntegrityViolationException e) {
            // This catch block will handle any constraints violation, like unique constraints
            throw new DataIntegrityViolationException("Unable to save tiger due to integrity constraint violation");
        }
    }
}
