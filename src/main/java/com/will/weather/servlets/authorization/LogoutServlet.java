package com.will.weather.servlets.authorization;

import com.will.weather.repository.HibernateSessionRepository;
import com.will.weather.models.UserSession;
import com.will.weather.servlets.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends BaseServlet {
    private final HibernateSessionRepository hibernateSessionRepository = new HibernateSessionRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем куки из запроса
        Cookie[] cookies = req.getCookies();
        // Проверяем содержит ли куки sessionId
        String sessionId = getCookieByName(cookies);
        Optional<UserSession> userSession = hibernateSessionRepository.findBySessionId(sessionId);
        hibernateSessionRepository.remove(userSession.get());
        Cookie cookie = new Cookie("sessionId", null);
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}