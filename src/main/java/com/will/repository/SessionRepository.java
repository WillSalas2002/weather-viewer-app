package com.will.repository;

import com.will.model.UserSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final SessionFactory sessionFactory;

    public Optional<UserSession> findUserSessionById(UUID uuid) {
        Optional<UserSession> result = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            UserSession userSession = session.get(UserSession.class, uuid);
            result = Optional.ofNullable(userSession);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void save(UserSession userSession) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(userSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        }
    }
}
