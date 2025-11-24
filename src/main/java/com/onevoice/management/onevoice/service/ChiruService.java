package com.onevoice.management.onevoice.service;

import com.onevoice.management.onevoice.model.Complaint;
import com.onevoice.management.onevoice.model.Event;
import com.onevoice.management.onevoice.model.Individual;
import com.onevoice.management.onevoice.repository.ComplaintRepository;
import com.onevoice.management.onevoice.repository.EventRepository;
import com.onevoice.management.onevoice.repository.IndividualRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChiruService {

    private final WebClient webClient;

    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private EventRepository eventRepository;

    public ChiruService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://127.0.0.1:11434") // Ollama local model running locally
                .build();
    }


    public String getChatResponse(String userMessage, Principal principal) {
        try {
            String email = principal.getName();
            Individual user = individualRepository.findByEmailId(email);


            String intentResponse = handleIntent(userMessage, user);
            if (intentResponse != null) {
                return intentResponse;
            }


            String systemPrompt = """
            You are Chiru — the official AI assistant of the OneVoice platform.

             About OneVoice:
            - OneVoice is a community-based web platform that connects citizens, organizations, and local bodies.
            - It allows users to register complaints, post events/news, view announcements, and track updates.
            - Technologies used:
                • Frontend: HTML, CSS, JavaScript, Thymeleaf
                • Backend: Java (Spring Boot Framework)
                • Database: PostgreSQL
                • Libraries: Spring Data JPA (Hibernate), Spring Security, Lombok
                • AI: Ollama local models (like gemma3:1b) for AI chat and responses.

            Chiru’s Objectives:
            - Help users interact with the OneVoice system through chat.
            - Answer questions related to posting events, complaint registration, and viewing case status.
            - Fetch and display complaint/event info (if implemented).
            - Respond *only* to OneVoice-related topics.
            - Stay friendly, polite, and clear.

            Example Conversations:
            User: What is OneVoice?
            Chiru: OneVoice is a community engagement platform for complaints, events, and updates — all in one place.

            User: How can I register a complaint?
            Chiru: In your dashboard, go to “Register Complaint,” fill in the issue details, and submit.

            User: How can I check my complaint status?
            Chiru: Open “Get Complaint Status” in your dashboard to see your pending or resolved cases.

            User: Show my pending complaints.
            Chiru: I’ll check your pending complaints and show them below (fetched from ComplaintRepository).

            If a user asks something not related to OneVoice:
            “I’m sorry, I can only assist with OneVoice-related information.”
            """;


            String fullPrompt = systemPrompt + "\n\nUser: " + userMessage + "\nChiru:";


            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gemma3:1b");
            requestBody.put("prompt", fullPrompt);
            requestBody.put("stream", false);


            String response = webClient.post()
                    .uri("/api/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            JSONObject json = new JSONObject(response);
            return json.optString("response", "No response from Chiru.");
        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, I couldn’t get a response from the OneVoice assistant.";
        }
    }


    private String handleIntent(String userMessage, Individual user) {
        if (userMessage == null || user == null) return null;
        String msg = userMessage.toLowerCase();


        if (msg.contains("status") || msg.contains("my case") || msg.contains("complaint")) {
            List<Complaint> complaints = complaintRepository.findByIndividualId(user.getId());
            if (complaints.isEmpty()) {
                return "You don’t have any registered complaints yet. You can create one from your dashboard under 'Register Complaint'.";
            }

            List<String> pending = complaints.stream()
                    .filter(c -> c.getStatus() != null && c.getStatus().name().equalsIgnoreCase("PENDING"))
                    .map(c -> "Complaint ID: " + c.getId() + " — " + c.getIssueDescription())
                    .collect(Collectors.toList());

            if (pending.isEmpty()) {
                return "Good news! You don’t have any pending complaints. All your issues seem resolved.";
            } else {
                return "You currently have " + pending.size() + " pending complaint(s):\n" + String.join("\n", pending);
            }
        }


        if (msg.contains("event") || msg.contains("news") || msg.contains("announcement")) {
            List<Event> events = eventRepository.findAll();
            if (events.isEmpty()) {
                return "Currently, there are no active events or announcements.";
            }

            List<Event> latestEvents = events.stream()
                    .sorted(Comparator.comparing(Event::getEventDate).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            String eventList = latestEvents.stream()
                    .map(e -> "• " + e.getTitle() + " — " + e.getEventDate())
                    .collect(Collectors.joining("\n"));

            return "Here are the latest events and announcements:\n" + eventList;
        }


        if (msg.contains("register") && msg.contains("complaint")) {
            return "To register a complaint, go to your dashboard and click on 'Register Complaint'. Fill in details like village, area, and issue description, then submit.";
        }

        if (msg.contains("view") && msg.contains("status")) {
            return "To view your complaint status, go to your dashboard and click on 'Get Complaint Status'. You’ll see all your submitted cases and their progress.";
        }

        if (msg.contains("post") && msg.contains("event")) {
            return "To post an event, go to your dashboard → 'Events' section → 'Post Event' and fill in the event details like title, date, and description.";
        }


        return null;
    }
}
