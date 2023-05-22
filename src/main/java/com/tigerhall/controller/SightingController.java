package com.tigerhall.controller;

import com.tigerhall.entity.Sighting;
import com.tigerhall.entity.Tiger;
import com.tigerhall.model.CreateSightingResponse;
import com.tigerhall.model.SightingInput;
import com.tigerhall.service.SightingService;
import com.tigerhall.service.TigerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SightingController {
    private final SightingService sightingService;
    private final TigerService tigerService;

    public SightingController(SightingService sightingService, TigerService tigerService) {
        this.sightingService = sightingService;
        this.tigerService = tigerService;
    }

    /**
     * Retrieves a paginated list of all sightings for a specific tiger.
     *
     * @param tigerId The ID of the tiger
     * @param page    The page number
     * @param size    The number of items per page
     * @return A page of sighting objects
     */
    @QueryMapping
    public Page<Sighting> allSightings(@Argument Long tigerId, @Argument Integer page, @Argument Integer size) {
        return sightingService.findAllSightingsByTigerId(tigerId, PageRequest.of(page, size));
    }

    /**
     * Creates a new sighting for a specific tiger.
     *
     * @param sightingInput The input data for creating a sighting
     * @return The response object containing the created sighting or an error message
     */
    @MutationMapping
    public CreateSightingResponse createSighting(@Argument SightingInput sightingInput) {
        try {
            Sighting sighting = new Sighting();

            // Fetch the Tiger by its ID
            Tiger tiger = tigerService.findTigerById(sightingInput.getTigerId())
                    .orElseThrow(() -> new IllegalArgumentException("Tiger with id " + sightingInput.getTigerId() + " does not exist"));

            sighting.setTiger(tiger);
            sighting.setTimestamp(sightingInput.getTimestamp());
            sighting.setLatitude(sightingInput.getLatitude());
            sighting.setLongitude(sightingInput.getLongitude());
            sighting.setImage(sightingInput.getImage());
            Sighting createdSighting = sightingService.saveSighting(sighting);
            return new CreateSightingResponse(createdSighting, null);
        } catch (Exception e) {
            return new CreateSightingResponse(null, e.getMessage());
        }
    }
}
