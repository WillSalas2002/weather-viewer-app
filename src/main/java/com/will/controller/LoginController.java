package com.will.controller;

import com.will.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import com.will.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginGet(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("userDto") UserDto userDto, Model model, HttpServletResponse response) {
        UserDto authenticatedUser = userService.authenticate(userDto, response);
        model.addAttribute("userDto", authenticatedUser);
        System.out.println(authenticatedUser);
        return "home";
    }
}
