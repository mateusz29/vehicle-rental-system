package org.example.vehicle.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.repository.api.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class VehiclePersistenceRepository implements VehicleRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Vehicle> find(UUID id) {
        Vehicle vehicle = em.find(Vehicle.class, id);
        em.refresh(vehicle);
        return Optional.ofNullable(vehicle);
    }

    @Override
    public List<Vehicle> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = cb.createQuery(Vehicle.class);
        Root<Vehicle> root = query.from(Vehicle.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Vehicle entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Vehicle entity) {
        Vehicle managed = em.find(Vehicle.class, entity.getId());
        em.refresh(managed);
        em.remove(managed);
    }

    @Override
    public void update(Vehicle entity) {
        em.merge(entity);
    }
}
