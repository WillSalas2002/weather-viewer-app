package com.will.weather.servlets.authorization;

import com.will.weather.repository.HibernateSessionRepository;
import com.will.weather.repository.HibernateUserRepository;
import com.will.weather.models.UserSession;
import com.will.weather.models.User;
import com.will.weather.servlets.BaseServlet;
import com.will.weather.utils.ThymeleafUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    private final HibernateUserRepository hibernateUserRepository = new HibernateUserRepository();
    private final HibernateSessionRepository hibernateSessionRepository = new HibernateSessionRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());
        templateEngine.process("login", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Получаем данные из формы авторизации
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        // Получаем и проверяем данные пользователя
        Optional<User> userOptional = hibernateUserRepository.findUserByLogin(login);
        if (userOptional.isPresent() && checkCredentials(userOptional.get(), password)) {
            User user = userOptional.get();
            UserSession userSession = user.getUserSession();
            // проверяем срок сессии
            if (userSession != null) {
                // если срок не иссякла, то берём её и создаём для неё куки, таким образом для одного юзера не будут две сессии в бд
                if (!isSessionExpired(userSession)) {
                    Cookie cookie = new Cookie("sessionId", userSession.getId());
                    cookie.setMaxAge(60 * 60);
                    resp.addCookie(cookie);
                    // Перенаправляем пользователя в персональный кабинет
                    resp.sendRedirect(req.getContextPath() + "/home");
                    return;
                    // в противном случае, удаляем сессию из бд и заново геренируем сессию
                } else {
                    hibernateSessionRepository.remove(userSession);
                }
            }
            // Генерируем уникальный идентификатор сессии
            String sessionId = generateSessionId();
            // Сохраняем ID пользователя и идентификатор сессии в базе данных
            UserSession newUserSession = new UserSession(sessionId, userOptional.get(), LocalDateTime.now().plusHours(1));
            hibernateSessionRepository.save(newUserSession);
            // Устанавливаем идентификатор сессии в cookies HTTP ответа
            Cookie cookie = new Cookie("sessionId", sessionId);
            cookie.setMaxAge(60 * 60);
            resp.addCookie(cookie);
            // Перенаправляем пользователя в персональный кабинет
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            // В случае неправильных данных перенаправляем пользователя в ту же страницу вместе с ошибкой
            context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());
            context.setVariable("error", "Incorrect password or login");
            templateEngine.process("login", context, resp.getWriter());
        }
    }

    private boolean checkCredentials(User user, String password) {
        return Objects.equals(user.getPassword(), password);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    private boolean isSessionExpired(UserSession userSession) {
        return userSession.getExpiresAt().isBefore(LocalDateTime.now());
    }
}
