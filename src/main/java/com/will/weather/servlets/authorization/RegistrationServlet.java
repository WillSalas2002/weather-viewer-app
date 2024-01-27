package com.will.weather.servlets.authorization;

import com.will.weather.dao.HibernateUserRepository;
import com.will.weather.models.User;
import com.will.weather.servlets.BaseServlet;
import com.will.weather.utils.ThymeleafUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends BaseServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext context;
    private final HibernateUserRepository hibernateUserRepository = new HibernateUserRepository();

    @Override
    public void init() {
        templateEngine = ThymeleafUtil.templateEngine(getServletContext());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Получаем данные из формы регистрации
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        // Сохраняем пользователя в базе данных
        User user = new User(login, password);
        hibernateUserRepository.saveUser(user);
        // Перенаправляем клиента в страницу login
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());
        templateEngine.process("register", context, resp.getWriter());
    }
}
