package org.example.vehicle.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.user.entity.User;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Rental_;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.repository.api.RentalRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rental> query = cb.createQuery(Rental.class);
        Root<Rental> root = query.from(Rental.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Rental entity) {
        em.persist(entity);
        em.refresh(em.find(Vehicle.class, entity.getVehicle().getId()));
    }

    @Override
    public void delete(Rental entity) {
        em.remove(em.find(Rental.class, entity.getId()));
    }

    @Override
    public void update(Rental entity) {
        em.merge(entity);
    }

    @Override
    public List<Rental> findAllByVehicleAndUser(Vehicle vehicle, User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rental> query = cb.createQuery(Rental.class);
        Root<Rental> root = query.from(Rental.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Rental_.vehicle), vehicle),
                        cb.equal(root.get(Rental_.user), user)
                ));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Rental> findByIdAndUser(UUID id, User user) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Rental> query = cb.createQuery(Rental.class);
            Root<Rental> root = query.from(Rental.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Rental_.user), user),
                            cb.equal(root.get(Rental_.id), id)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Rental> findAllByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rental> query = cb.createQuery(Rental.class);
        Root<Rental> root = query.from(Rental.class);
        query.select(root)
                .where(cb.equal(root.get(Rental_.user), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Rental> findAllByVehicle(Vehicle vehicle) {
        return vehicle.getRentals();
    }
}
