package com.tmorris.reunitedtrav.services;

import com.tmorris.reunitedtrav.controllers.jsonbodies.EventRequest;
import com.tmorris.reunitedtrav.models.Address;
import com.tmorris.reunitedtrav.models.Event;
import com.tmorris.reunitedtrav.models.enums.Status;
import com.tmorris.reunitedtrav.models.enums.EventType;
import com.tmorris.reunitedtrav.repositories.EventRepository;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(EventRequest request) {
        Event event = Event.builder()
                .eventType(EnumUtils.getEnumIgnoreCase(EventType.class, request.getType()))
                .endTime(request.getEndTime())
                .startTime(request.getStartTime())
                .description(request.getDescription())
                .images(request.getImages())
                .maximumAmountOfPeople(request.getMaximumAmountOfPeople())
                .minimumAmountOfPeople(request.getMinimumAmountOfPeople())
                .name(request.getName())
                .address(Address.builder()
                        .zipCode(request.getZipCode())
                        .streetOne(request.getStreetOne())
                        .streetTwo(request.getStreetTwo())
                        .state(request.getState())
                        .city(request.getCity())
                        .apartmentNumber(request.getApartmentNumber())
                        .build())
                .adultOnly(request.getAdultOnly())
                .notes(request.getNotes())
                .status(EnumUtils.getEnumIgnoreCase(Status.class, request.getStatus()))
                .build();

        eventRepository.save(event);

        return event;
    }

    public Event getEventByUuid(UUID uuid) {
        return eventRepository
                .findByUuid(uuid)
                .orElse(null);
    }

    public List<Event> getEvents() {
        return eventRepository
                .findAll();
    }
}
