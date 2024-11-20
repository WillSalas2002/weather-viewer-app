package com.will.controller;

import com.will.dto.UserDto;
import com.will.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/home")
    public String getHomePage(Model model, HttpServletRequest request) {
        Cookie cookie = getCookie(request);
        String sessionId = cookie.getValue();
        UserDto userDto = userService.findUserBySessionId(sessionId);
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
