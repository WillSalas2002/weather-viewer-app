package com.will.controller;

import com.will.dto.LocationDto;
import com.will.service.ForecastService;
import com.will.service.LocationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private final ForecastService forecastService;
    private final LocationService locationService;

    @GetMapping
    public String locationPage(@RequestParam("city") String city, Model model) throws URISyntaxException, IOException, InterruptedException {
        // TODO: need to validate city, if not valid return to the user;
        List<LocationDto> locations = forecastService.getCoordinatesByCity(city);
        model.addAttribute("locations", locations);
        model.addAttribute("locationDto", new LocationDto());
        return "location";
    }

    @PostMapping
    public String location(@ModelAttribute("locationDto") LocationDto locationDto, HttpServletRequest request) {
        Cookie cookie = getCookie(request);
        String sessionId = cookie.getValue();
        locationService.save(locationDto, sessionId);
        return "redirect:/home";
    }

    @DeleteMapping
    public String delete(@RequestParam("lon") String lon, @RequestParam("lat") String lat, HttpServletRequest request) {
        Cookie cookie = getCookie(request);
        String sessionId = cookie.getValue();
        locationService.remove(lon, lat, sessionId);
        return "redirect:/home";
    }

    private Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
