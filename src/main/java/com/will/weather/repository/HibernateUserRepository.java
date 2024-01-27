package com.will.weather.repository;

import com.will.weather.models.User;
import com.will.weather.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class HibernateUserRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Optional<User> findUserByLogin(String login) {
        Optional<User> user;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            user = session.createQuery("FROM User WHERE login =:login", User.class)
                    .setParameter("login", login).stream().findAny();
            session.getTransaction().commit();
        }
        return user;
    }

    public void saveUser(User user) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }
}