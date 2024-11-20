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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserMapper userMapper;
    private final SessionRepository sessionRepository;

    public boolean isSessionValid(String id) {
        return sessionRepository.findSessionById(UUID.fromString(id))
                .map(session -> session.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    public void createAndAttachSession(UserDto userDto, HttpServletResponse response) {
        User user = userMapper.toUser(userDto);
        UserSession userSession = new UserSession();
        UUID uuid = UUID.randomUUID();
        userSession.setUuid(uuid);
        userSession.setExpiresAt(LocalDateTime.now().plusHours(1));
        userSession.setUser(user);

        Cookie cookie = new Cookie("sessionId", uuid.toString());
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        sessionRepository.save(userSession);
    }

    public UserSession findUserSessionById(UUID uuid) {
        return sessionRepository.findSessionById(uuid)
                .orElseThrow(() -> new RuntimeException("Session with this id not found " + uuid));
    }

    public void deleteSession(UUID uuid) {
        sessionRepository.deleteSession(uuid);
    }

    public List<UserSession> getUserSessions(User user) {
        return sessionRepository.findSessionsByUserId(user.getId());
    }
}
