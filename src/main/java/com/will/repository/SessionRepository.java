package com.will.repository;

import com.will.model.User;
import com.will.model.UserSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final SessionFactory sessionFactory;

    public Optional<UserSession> findSessionById(UUID uuid) {
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
            User mergedUser = session.merge(userSession.getUser());
            userSession.setUser(mergedUser);
            session.persist(userSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        }
    }

    public void deleteSession(UUID uuid) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM UserSession us WHERE us.uuid =: uuid")
                    .setParameter("uuid", uuid)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        }
    }

    public List<UserSession> findSessionsByUserId(Long userId) {
        Transaction transaction = null;
        List<UserSession> result = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            result = session.createQuery("FROM UserSession us WHERE us.user.id =: userId", UserSession.class)
                    .setParameter("userId", userId)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        }
        return result;
    }
}
