package org.example.vehicle.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.user.entity.User;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.repository.api.RentalRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class RentalPersistenceRepository implements RentalRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Rental> find (UUID id) {
        return Optional.ofNullable(em.find(Rental.class, id));
    }

    @Override
    public List<Rental> findAll() {
        return em.createQuery("select r from Rental r", Rental.class).getResultList();
    }

    @Override
    public void create(Rental entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Rental entity) {
        em.refresh(entity);
        em.remove(em.find(Rental.class, entity.getId()));
    }

    @Override
    public void update(Rental entity) {
        em.merge(entity);
    }

    @Override
    public List<Rental> findAllByUser(User user) {
        return em.createQuery("select r from Rental r where r.user = :user", Rental.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Rental> findAllByVehicle(Vehicle vehicle) {
        return em.createQuery("select r from Rental r where r.vehicle = :vehicle", Rental.class)
                .setParameter("vehicle", vehicle)
                .getResultList();
    }
}
