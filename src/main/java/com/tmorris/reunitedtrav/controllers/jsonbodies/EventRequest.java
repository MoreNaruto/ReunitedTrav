package com.tmorris.reunitedtrav.controllers.jsonbodies;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    private String type;
    private String status;
    private String name;
    private String description;
    private Integer minimumAmountOfPeople;
    private Integer maximumAmountOfPeople;
    private List<String> images;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String streetOne;
    private String streetTwo;
    private String apartmentNumber;
    private String city;
    private String state;
    private String zipCode;
    private Boolean adultOnly;
    private String notes;
}
