package com.will.service;

import com.will.dto.UserDto;
import com.will.mapper.UserMapper;
import com.will.model.User;
import com.will.model.UserSession;
import com.will.repository.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserMapper userMapper;
    private final SessionRepository sessionRepository;

    public boolean isExpired(String id) {
        Optional<UserSession> userSessionOptional = sessionRepository.findUserSessionById(UUID.fromString(id));
        if (userSessionOptional.isPresent()) {
            UserSession userSession = userSessionOptional.get();
            return userSession.getExpiresAt().isBefore(LocalDateTime.now());
        }
        // Scheduler has deleted
        return true;
    }

    public void createAndAttachSession(UserDto userDto, HttpServletResponse response) {
        User user = userMapper.toUser(userDto);
        UserSession userSession = new UserSession();
        UUID uuid = UUID.randomUUID();
        userSession.setUuid(uuid);
        userSession.setExpiresAt(LocalDateTime.now().plusHours(1));
        userSession.setUser(user);

        Cookie cookie = new Cookie("sessionId", uuid.toString());
        response.addCookie(cookie);

        sessionRepository.save(userSession);
    }
}
