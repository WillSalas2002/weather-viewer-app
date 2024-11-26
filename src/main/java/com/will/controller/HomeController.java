package com.will.controller;

import com.will.dto.UserDto;
import com.will.entity.Coord;
import com.will.service.ForecastService;
import com.will.service.LocationService;
import com.will.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final LocationService locationService;
    private final ForecastService forecastService;

    @GetMapping("/home")
    public String getHomePage(Model model, HttpServletRequest request) throws URISyntaxException, IOException, InterruptedException {
        Cookie cookie = getCookie(request);
        String sessionId = cookie.getValue();
        UserDto userDto = userService.findUserBySessionId(sessionId);
        List<Coord> coordList = locationService.findSavedCoordinates(userDto);
//        if (!coordList.isEmpty()) {
//            forecastService.getForecastByCoordinates(coordList);
//        }
        model.addAttribute("userDto", userDto);
        return "home";
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
