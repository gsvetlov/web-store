package ru.svetlov.webstore.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.domain.Product;
import ru.svetlov.webstore.repository.ProductRepository;
import ru.svetlov.webstore.repository.factory.SessionFactoryProvider;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class HibernateDao implements ProductRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public HibernateDao(SessionFactoryProvider provider) {
        sessionFactory = provider.getInstance();
    }

    @Override
    public Optional<Product> getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Product.class, id));
        }
    }

    @Override
    public List<Product> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createNamedQuery("Product.getAll", Product.class).getResultList();
        }
    }

    @Override
    public Product create(String title, double cost) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Product product = new Product(title, cost);
            session.save(product);
            session.getTransaction().commit();
            return product;
        }
    }

    @Override
    public void update(Product product) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.update(product);
            session.getTransaction().commit();
        }

    }
}
