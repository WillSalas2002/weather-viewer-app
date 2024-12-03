package com.will.repository;

import com.will.model.Location;
import com.will.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

            Location managedLocation = getLocationByCoord(location.getLongitude(), location.getLatitude(), session);

            if (managedLocation == null) {
                managedLocation = session.merge(location);
            }
            User managedUser = session.get(User.class, user.getId());

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

    public void removeFromUserLocation(BigDecimal lon, BigDecimal lat, User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User managedUser = session.get(User.class, user.getId());
            Location location = getLocationByCoord(lon, lat, session);

            if (managedUser.getLocations().contains(location)) {
                location.getUsers().remove(managedUser);
                managedUser.getLocations().remove(location);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                System.out.println(e.getMessage());
                transaction.rollback();
            }
        }
    }

    private static Location getLocationByCoord(BigDecimal lon, BigDecimal lat, Session session) {
        return session.createQuery("SELECT l FROM Location l WHERE l.longitude =: lon AND l.latitude =: lat", Location.class)
                .setParameter("lon", lon)
                .setParameter("lat", lat)
                .uniqueResult();
    }
}
