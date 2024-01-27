package com.will.weather.repository;

import com.will.weather.models.UserSession;
import com.will.weather.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class HibernateSessionRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void save(UserSession userSession) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(userSession);
            session.getTransaction().commit();
        }
    }

    public Optional<UserSession> findBySessionId(String sessionId) {
        Optional<UserSession> userSession;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            userSession = session.createQuery("FROM UserSession WHERE id =:id", UserSession.class)
                    .setParameter("id", sessionId).stream().findAny();
            session.getTransaction().commit();
        }
        return userSession;
    }

    public void remove(UserSession userSession) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.remove(userSession);
            session.getTransaction().commit();
        }
    }
}