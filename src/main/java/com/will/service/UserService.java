package com.will.service;

import com.will.dto.UserDto;
import com.will.exception.IncorrectCredentialsException;
import com.will.exception.UserNotFoundException;
import com.will.mapper.UserMapper;
import com.will.model.User;
import com.will.model.UserSession;
import com.will.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final SessionService sessionService;

    public UserDto authenticate(UserDto userDto, HttpServletResponse response) {
        User user = userRepository.findUserByLogin(userDto.getLogin())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new IncorrectCredentialsException("Incorrect password");
        }
        userDto.setId(user.getId());

        List<UserSession> userSessions = sessionService.getUserSessions(user);
        for (UserSession userSession : userSessions) {
            UUID uuid = userSession.getUuid();
            if (!sessionService.isSessionValid(uuid.toString())) {
                sessionService.deleteSession(uuid);
            } else {
                // I am updating cookie on the user side, so that the new user won't have old user's sessionId
                Cookie cookie = new Cookie("sessionId", uuid.toString());
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);
                return userMapper.toUserDto(user);
            }
        }
        sessionService.createAndAttachSession(userDto, response);

        return userMapper.toUserDto(user);
    }

    public UserDto findUserBySessionId(String sessionId) {
        UserSession userSession = sessionService.findUserSessionById(UUID.fromString(sessionId));
        User user = userRepository.findUserBySessionId(userSession).orElseThrow(() -> new RuntimeException("User by this session not found " + userSession.getUuid()));
        return userMapper.toUserDto(user);
    }

}
