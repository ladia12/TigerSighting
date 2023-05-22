package com.tigerhall.controller;

import com.tigerhall.entity.Tiger;
import com.tigerhall.model.CreateTigerResponse;
import com.tigerhall.model.TigerInput;
import com.tigerhall.service.TigerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TigerController {
    private final TigerService tigerService;

    public TigerController(TigerService tigerService) {
        this.tigerService = tigerService;
    }

    /**
     * Retrieves a paginated list of all tigers.
     *
     * @param page The page number
     * @param size The number of items per page
     * @return A page of tiger objects
     */
    @QueryMapping
    public Page<Tiger> allTigers(@Argument Integer page, @Argument Integer size) {
        return tigerService.findAllTigers(PageRequest.of(page, size));
    }

    /**
     * Retrieves a tiger by its ID.
     *
     * @param id The ID of the tiger
     * @return The tiger object
     * @throws IllegalArgumentException if the tiger does not exist
     */
    @QueryMapping
    public Tiger tiger(@Argument Long id) {
        return tigerService.findTigerById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tiger with id " + id + " does not exist"));
    }

    /**
     * Creates a new tiger.
     *
     * @param tigerInput The input data for creating a tiger
     * @return The response object containing the created tiger or an error message
     */
    @MutationMapping
    public CreateTigerResponse createTiger(@Argument TigerInput tigerInput) {
        try {
            Tiger tiger = new Tiger();
            tiger.setName(tigerInput.getName());
            tiger.setDateOfBirth(tigerInput.getDateOfBirth());
            tiger.setLastSeenTimestamp(tigerInput.getLastSeenTimestamp());
            tiger.setLastSeenLatitude(tigerInput.getLastSeenLatitude());
            tiger.setLastSeenLongitude(tigerInput.getLastSeenLongitude());
            Tiger createdTiger = tigerService.saveTiger(tiger);
            return new CreateTigerResponse(createdTiger, null);
        } catch (Exception e) {
            return new CreateTigerResponse(null, e.getMessage());
        }

    }
}
