package com.tigerhall.service;

import com.tigerhall.entity.Location;
import com.tigerhall.entity.Sighting;
import com.tigerhall.respository.SightingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SightingService {
    private final SightingRepository sightingRepository;

    public SightingService(SightingRepository sightingRepository) {
        this.sightingRepository = sightingRepository;
    }

    /**
     * Saves a sighting.
     *
     * @param sighting The sighting object to be saved
     * @return The saved sighting object
     * @throws IllegalArgumentException if the sighting data is invalid
     */
    public Sighting saveSighting(Sighting sighting) throws IllegalArgumentException {
        if (sighting.getTiger() == null) {
            throw new IllegalArgumentException("Tiger cannot be null");
        }

        if (sighting.getTimestamp() == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        if (sighting.getLatitude() == null || sighting.getLongitude() == null) {
            throw new IllegalArgumentException("Coordinates cannot be null or empty");
        }

        // Fetch the last sighting of the tiger
        Sighting lastSighting = sightingRepository.findTopByTigerOrderByTimestampDesc(sighting.getTiger())
                .orElse(null);

        if (lastSighting != null) {
            // Calculate the distance between the last sighting and the new one
            double distance = calculateDistance(new Location(lastSighting.getLatitude(), lastSighting.getLongitude()),
                    new Location(sighting.getLatitude(), sighting.getLongitude()));

            // If the new sighting is within 5 kilometers of the last one, throw an exception
            if (distance < 5) {
                throw new IllegalArgumentException("The new sighting is within 5 kilometers of the last sighting.");
            }
        }

        return sightingRepository.save(sighting);
    }

    /**
     * Calculates the distance between two sets of coordinates.
     *
     * @param coordinates1 The first set of coordinates
     * @param coordinates2 The second set of coordinates
     * @return The distance between the coordinates in kilometers
     */
    private double calculateDistance(Location coordinates1, Location coordinates2) {

        double lat1 = coordinates1.getLatitude();
        double lon1 = coordinates1.getLongitude();
        double lat2 = coordinates2.getLatitude();
        double lon2 = coordinates2.getLongitude();

        int earthRadius = 6371; // Earth radius in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    /**
     * Retrieves a paginated list of all sightings for a given tiger.
     *
     * @param tigerId  The ID of the tiger
     * @param pageable The pageable object specifying the page number and size
     * @return A page of sighting objects
     */
    public Page<Sighting> findAllSightingsByTigerId(Long tigerId, Pageable pageable) {
        return sightingRepository.findByTiger_IdOrderByTimestampDesc(tigerId, pageable);
    }
}

