package com.will.filter;

import com.will.service.SessionService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

@Slf4j
@WebFilter("/*")
public class AuthFilter implements Filter {

    private final String COOKIE_NAME = "sessionId";
    private SessionService sessionService;

    // SessionService should be added to the context explicitly, otherwise filter throws error
    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        sessionService = ctx.getBean(SessionService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        Cookie cookie = getSessionIdFromCookie(request);
        if (requestURI.equals(request.getContextPath() + "/login") ||
                requestURI.equals(request.getContextPath() + "/registration")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (cookie == null) {
            log.info("User doesn't have a cookie, redirecting them to login page");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String sessionId = cookie.getValue();
        if (sessionService.isSessionValid(sessionId)) {
            filterChain.doFilter(request, response);
        } else {
            log.info("User session has expired, deleting cookie and redirecting to login page");
//            sessionId.deleteCookie();
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    private Cookie getSessionIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
