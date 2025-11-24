package com.onevoice.management.onevoice.controllers;

import com.onevoice.management.onevoice.model.Individual;
import com.onevoice.management.onevoice.service.IndividualService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TalkToAIController {

    private final IndividualService individualService;

    public TalkToAIController(IndividualService individualService) {
        this.individualService = individualService;
    }

    @GetMapping("/talktoai")
    public String talkToAI(@AuthenticationPrincipal User user, Model model) {
        String email = user.getUsername();
        Individual individual = individualService.findByEmailId(email);
        model.addAttribute("username", individual.getName());
        return "talktoai";
    }
}
