package com.will.service;

import com.will.model.UserSession;
import com.will.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public boolean isExpired(String id) {
        Optional<UserSession> userSessionOptional = sessionRepository.findUserSessionById(id);
        if (userSessionOptional.isPresent()) {
            UserSession userSession = userSessionOptional.get();
            return userSession.getExpiresAt().isAfter(LocalDateTime.now());
        }
        // Scheduler has deleted
        return true;
    }
}
