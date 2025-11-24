package com.onevoice.management.onevoice.controllers;

import com.onevoice.management.onevoice.model.Event;
import com.onevoice.management.onevoice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public String listEvents(Model model, @RequestParam(required = false) String date) {
        List<Event> events;
        if (date != null && !date.isEmpty()) {
            LocalDate filterDate = LocalDate.parse(date);
            events = eventService.getEventsByDate(filterDate);
        } else {
            events = eventService.getAllEvents();
        }

        model.addAttribute("events", events);
        model.addAttribute("filterDate", date);
        return "events-news"; // Must match events-news.html
    }


    @GetMapping("/postEventPage")
    public String postEventPage(Model model) {
        return "post-event"; // Must match post-event.html
    }

    @PostMapping("/post")
    public String saveEvent(@RequestParam String title,
                            @RequestParam String description,
                            @RequestParam String status,
                            @RequestParam String date,
                            @RequestParam(required = false) MultipartFile image,
                            Principal principal) throws IOException {

        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setStatus(status);
        event.setEventDate(LocalDate.parse(date));

        if (principal != null) {
            event.setPostedBy(principal.getName());
        } else {
            event.setPostedBy("Anonymous");
        }

        if (image != null && !image.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();


            String uploadDir = "C:/Users/pruth/Downloads/onevoice/onevoice/uploads/";

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            File file = new File(uploadDir + filename);
            image.transferTo(file);

            event.setImagePath(filename);
        }

        eventService.saveEvent(event);
        System.out.println("✅ Event saved successfully: " + event.getTitle());

        return "redirect:/events";
    }
}
