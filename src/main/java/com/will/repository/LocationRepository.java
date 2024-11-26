package com.will.repository;

import com.will.model.Location;
import com.will.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationRepository {

    private final SessionFactory sessionFactory;

    public List<Location> findAllByUserId(Long id) {
        Transaction transaction = null;
        List<Location> locations = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            locations = session.createQuery("SELECT l FROM Location l JOIN l.users u WHERE u.id = :userId", Location.class)
                    .setParameter("userId", id)
                    .getResultList();

        } catch (Exception e) {
            if (transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        }
        return locations;
    }

    public void save(Location location, User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User managedUser = session.get(User.class, user.getId());
            Location managedLocation = session.merge(location);

            if (!managedUser.getLocations().contains(managedLocation)) {
                managedUser.getLocations().add(managedLocation);
                managedLocation.getUsers().add(managedUser);
                session.persist(managedUser);
                session.persist(managedLocation);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        }
    }
}
