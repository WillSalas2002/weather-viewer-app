package com.will.service;

import com.will.dto.UserDto;
import com.will.mapper.UserMapper;
import com.will.model.User;
import com.will.model.UserSession;
import com.will.repository.UserRepository;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        userDto.setId(user.getId());

        // if accessed multiple times, then session will be created each time((
        List<UserSession> userSessions = sessionService.getUserSessions(user);
        for (UserSession userSession : userSessions) {
            UUID uuid = userSession.getUuid();
            if (!sessionService.isSessionValid(uuid.toString())) {
                sessionService.deleteSession(uuid);
            } else {
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
