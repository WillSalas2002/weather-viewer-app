package com.will.weather.utils;

import com.will.weather.models.Location;
import com.will.weather.models.UserSession;
import com.will.weather.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

     static {
         try {
             sessionFactory = new Configuration()
                     .addAnnotatedClass(User.class)
                     .addAnnotatedClass(UserSession.class)
                     .addAnnotatedClass(Location.class)
                     .configure()
                     .buildSessionFactory();
         }catch (Throwable ex) {
             System.err.println("SessionFactory creation failed." + ex);
             throw new ExceptionInInitializerError(ex);
         }
     }

     public static SessionFactory getSessionFactory() {
         return sessionFactory;
     }
}
