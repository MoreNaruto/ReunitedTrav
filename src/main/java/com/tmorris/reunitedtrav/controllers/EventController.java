package com.tmorris.reunitedtrav.controllers;

import com.tmorris.reunitedtrav.controllers.jsonbodies.EventRequest;
import com.tmorris.reunitedtrav.models.Event;
import com.tmorris.reunitedtrav.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EventRequest request) {
        Event event = eventService.create(request);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Event> getEvent(@PathVariable UUID uuid) {
        Event event = eventService.getEventByUuid(uuid);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }
}
