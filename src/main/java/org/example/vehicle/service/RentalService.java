package org.example.vehicle.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import org.example.user.entity.User;
import org.example.user.entity.UserRoles;
import org.example.user.repository.api.UserRepository;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.repository.api.RentalRepository;
import org.example.vehicle.repository.api.VehicleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class RentalService {
    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final SecurityContext securityContext;

    @Inject
    public RentalService(
            VehicleRepository vehicleRepository,
            RentalRepository rentalRepository,
            UserRepository userRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext
    ) {
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Rental> find(UUID id) {
        return rentalRepository.find(id);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Rental> find(User user, UUID id) {
        return rentalRepository.findByIdAndUser(id, user);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Rental> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return find(id);
        }
        User user = userRepository.findByUsername(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @RolesAllowed(UserRoles.USER)
    public List<Rental> findAll(User user) {
        return rentalRepository.findAllByUser(user);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Rental> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return findAll();
        }
        User user = userRepository.findByUsername(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Rental rental) {
        if (rentalRepository.find(rental.getId()).isPresent()) {
            throw new IllegalArgumentException("Rental with id \"%s\" already exists".formatted(rental.getId()));
        }
        if (vehicleRepository.find(rental.getVehicle().getId()).isEmpty()) {
            throw new NotFoundException("The vehicle with id \"%s\" does not exist".formatted(rental.getVehicle().getId()));
        }
        rentalRepository.create(rental);
    }

    @RolesAllowed(UserRoles.USER)
    public void createForCallerPrincipal(Rental rental) {
        User user = userRepository.findByUsername(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        rental.setUser(user);
        create(rental);
    }

    @RolesAllowed(UserRoles.USER)
    public void update(Rental rental) {
        checkAdminRoleOrOwner(rentalRepository.find(rental.getId()));
        rentalRepository.update(rental);
    }

    @RolesAllowed(UserRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(rentalRepository.find(id));
        rentalRepository.delete(rentalRepository.find(id).orElseThrow());
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<List<Rental>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(rentalRepository::findAllByUser);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<List<Rental>> findAllByVehicle(UUID id) {
        return vehicleRepository.find(id)
                .map(rentalRepository::findAllByVehicle);
    }

    private void checkAdminRoleOrOwner(Optional<Rental> rental) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && rental.isPresent()
                && rental.get().getUser().getUsername().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
