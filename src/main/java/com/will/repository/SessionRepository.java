package com.will.repository;

import com.will.model.UserSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private SessionFactory sessionFactory;

    public Optional<UserSession> findUserSessionById(String id) {
        Transaction transaction = null;
        Optional<UserSession> result = Optional.empty();
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            result = Optional.ofNullable(session.get(UserSession.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }
}
