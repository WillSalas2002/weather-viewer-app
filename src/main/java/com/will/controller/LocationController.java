package com.will.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/location")
public class LocationController {

    @GetMapping
    public String locationPage(@RequestParam("locationName") String locationName, Model model) {
        model.addAttribute("location", locationName);
        return "locations";
    }

    @PostMapping
    public String location() {
        // TODO: need to save the chosen location and redirect to home page;
        return "redirect:/home";
    }

}
