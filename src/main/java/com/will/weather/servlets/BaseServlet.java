package com.will.weather.servlets;

import com.will.weather.utils.ThymeleafUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

public abstract class BaseServlet extends HttpServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext context;

    @Override
    public void init() {
        templateEngine = ThymeleafUtil.templateEngine(getServletContext());
    }

    protected String getCookieByName(Cookie[] cookies) {
        String sessionId = null;
        // Проверяем содержит ли куки sessionId
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.getAttribute("sessionId");
                if (cookie.getName().equals("sessionId")) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }
}
