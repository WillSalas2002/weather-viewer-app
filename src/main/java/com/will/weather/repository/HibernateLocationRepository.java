package com.will.weather.repository;

import com.will.weather.models.Location;
import com.will.weather.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class HibernateLocationRepository {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public Optional<Location> findByLocationName(String cityName) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Location> locationOptional = session.createQuery("FROM Location WHERE name =:cityName", Location.class)
                    .setParameter("cityName", cityName).stream().findAny();
            session.getTransaction().commit();
            return locationOptional;
        }
    }

    public void save(Location location) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(location);
            session.getTransaction().commit();
        }
    }
}
