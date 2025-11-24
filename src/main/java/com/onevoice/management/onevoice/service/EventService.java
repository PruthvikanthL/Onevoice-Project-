package com.onevoice.management.onevoice.service;

import com.onevoice.management.onevoice.model.Event;
import com.onevoice.management.onevoice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByDate(LocalDate date) {
        return eventRepository.findByEventDate(date);
    }
}
