package com.will.repository;

import com.will.model.User;
import com.will.model.UserSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SessionFactory sessionFactory;

    public Optional<User> findUserByLogin(String login) {
        Transaction transaction;
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            User user = currentSession.createQuery("FROM User u WHERE u.login =: login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            transaction.commit();
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> findUserBySessionId(UserSession userSession) {
        Transaction transaction;
        try (Session currentSession = sessionFactory.getCurrentSession()) {
            transaction = currentSession.beginTransaction();
            User user = currentSession.createQuery("SELECT u FROM User u JOIN u.userSession us WHERE us = :userSession", User.class)
                    .setParameter("userSession", userSession)
                    .uniqueResult();
            transaction.commit();
            return Optional.ofNullable(user);
        }
    }
}
