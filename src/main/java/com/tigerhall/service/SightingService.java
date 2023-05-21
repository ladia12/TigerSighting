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

    public Page<Sighting> findAllSightingsByTigerId(Long tigerId, Pageable pageable) {
        return sightingRepository.findByTiger_IdOrderByTimestampDesc(tigerId, pageable);
    }
}

