package ru.svetlov.webstore.repository.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;


@Component
public class SessionFactoryProvider {
    private final SessionFactory factory;

    public SessionFactoryProvider() {
        factory = new Configuration().configure("classpath:hibernate.cfg.xml").buildSessionFactory();
    }

    public SessionFactory getInstance() {
        return factory;
    }
}
