package com.will.controller;

import com.will.dto.UserDto;
import com.will.service.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final SessionService sessionService;

    @GetMapping
    public String registrationPage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping
    public String registration(@ModelAttribute("userDto") UserDto userDto, HttpServletResponse response) {
        System.out.println(userDto.getLogin() + " " + userDto.getPassword());
        sessionService.createAndAttachSession(userDto, response);
        return "redirect:/home";
    }
}
