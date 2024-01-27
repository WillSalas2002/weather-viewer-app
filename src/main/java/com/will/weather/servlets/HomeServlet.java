package com.will.weather.servlets;

import com.will.weather.dao.HibernateLocationRepository;
import com.will.weather.dao.HibernateSessionRepository;
import com.will.weather.dao.HibernateUserRepository;
import com.will.weather.models.Location;
import com.will.weather.models.User;
import com.will.weather.models.UserSession;
import com.will.weather.service.JsonToJavaConverter;
import com.will.weather.service.WeatherClient;
import com.will.weather.service.dto.WeatherApiResponse;
import com.will.weather.utils.ThymeleafUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    private final HibernateSessionRepository hibernateSessionRepository = new HibernateSessionRepository();
    private final HibernateLocationRepository hibernateLocationRepository = new HibernateLocationRepository();
    private final HibernateUserRepository hibernateUserRepository = new HibernateUserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionId;
        // Получаем куки из запроса
        Cookie[] cookies = req.getCookies();
        // Проверяем содержит ли куки sessionId
        if (cookies != null) {
            sessionId = getCookieByName(cookies);
            Optional<UserSession> sessionOptional = hibernateSessionRepository.findBySessionId(sessionId);
            // Проверяем существует ли сессия с таким sessionId
            if (sessionOptional.isPresent()) {
                UserSession userSession = sessionOptional.get();
                if (!isSessionExpired(userSession)) {
                    // Получаем пользователя из сессии и отправляем в home.html
                    User user = userSession.getUser();
                    List<WeatherApiResponse> responseList = new ArrayList<>();
                    WeatherClient client = new WeatherClient();
                    for (Location location : user.getLocations()) {
                        String jsonString = client.getWeatherByCoordinates(location.getLatitude(), location.getLongitude());
                        JsonToJavaConverter converter = new JsonToJavaConverter(jsonString);
                        WeatherApiResponse response = converter.convertToWeatherResponse();
                        responseList.add(response);
                    }
                    context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());
                    context.setVariable("responseList", responseList);
                    context.setVariable("user", user);
                    templateEngine.process("home", context, resp.getWriter());
                    return;
                } else {
                    hibernateSessionRepository.remove(userSession);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String cityName = req.getParameter("locationName");
        BigDecimal latitude = new BigDecimal(req.getParameter("latitude"));
        BigDecimal longitude = new BigDecimal(req.getParameter("longitude"));

        Cookie[] cookies = req.getCookies();
        String sessionId = getCookieByName(cookies);
        Optional<UserSession> sessionOptional = hibernateSessionRepository.findBySessionId(sessionId);
        UserSession userSession = sessionOptional.get();
        User user = userSession.getUser();

        Optional<Location> locationOptional = hibernateLocationRepository.findByLocationName(cityName);
        Location location;
        if (locationOptional.isPresent()) {
            // If the location already exists, retrieve it from the database
            location = locationOptional.get();
        } else {
            // If the location doesn't exist, create a new location and save it to the database
            location = new Location(cityName, longitude, latitude);
            hibernateLocationRepository.save(location);
        }
        user.getLocations().add(location);
        location.getUsers().add(user);
        hibernateUserRepository.saveUser(user);
        doGet(req, resp);
    }

    private boolean isSessionExpired(UserSession userSession) {
        return userSession.getExpiresAt().isBefore(LocalDateTime.now());
    }
}

