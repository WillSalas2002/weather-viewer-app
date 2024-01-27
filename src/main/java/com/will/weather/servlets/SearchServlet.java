package com.will.weather.servlets;

import com.will.weather.dao.HibernateSessionRepository;
import com.will.weather.models.UserSession;
import com.will.weather.service.JsonToJavaConverter;
import com.will.weather.service.WeatherClient;
import com.will.weather.service.dto.LocationResponse;
import com.will.weather.utils.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/search")
public class SearchServlet extends BaseServlet {
    private final HibernateSessionRepository hibernateSessionRepository = new HibernateSessionRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        // Проверяем содержит ли куки sessionId
        if (cookies != null) {
            String sessionId = getCookieByName(cookies);
            Optional<UserSession> sessionOptional = hibernateSessionRepository.findBySessionId(sessionId);
            if (sessionOptional.isPresent()) {
                context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());
                context.setVariable("user", sessionOptional.get().getUser());
                templateEngine.process("search", context, resp.getWriter());
            }
        } else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем куки из запроса
        Cookie[] cookies = req.getCookies();
        String sessionId = getCookieByName(cookies);
        Optional<UserSession> sessionOptional = hibernateSessionRepository.findBySessionId(sessionId);
        String cityName = req.getParameter("search");
        WeatherClient weatherClient = new WeatherClient();
        JsonToJavaConverter converter = new JsonToJavaConverter(weatherClient.getWeatherByCityName(cityName));
        LocationResponse locationResponse = converter.convertToLocationResponse();
        context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());
        context.setVariable("user", sessionOptional.get().getUser());
        context.setVariable("locationResponse", locationResponse);
        templateEngine.process("search", context, resp.getWriter());
    }
}