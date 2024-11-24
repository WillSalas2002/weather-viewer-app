package com.will.controller;

import com.will.dto.LocationDto;
import com.will.service.ForecaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final ForecaseService forecaseService;

    @GetMapping
    public String locationPage(@RequestParam("city") String city, Model model) throws URISyntaxException, IOException, InterruptedException {
        // TODO: need to validate city, if not valid return to the user;
        List<LocationDto> locations = forecaseService.getCoordinatesByCity(city);
        model.addAttribute("locations", locations);
        return "location";
    }

    @PostMapping
    public String location() {
        // TODO: need to save the chosen location and redirect to home page;
        return "redirect:/home";
    }

}
