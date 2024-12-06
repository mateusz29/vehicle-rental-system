package org.example.vehicle.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.model.RentalCreateModel;
import org.example.vehicle.model.VehicleModel;
import org.example.vehicle.service.RentalService;
import org.example.vehicle.service.VehicleService;

import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
@NoArgsConstructor(force = true)
public class RentalCreate implements Serializable {
    private RentalService rentalService;
    private VehicleService vehicleService;
    private final ModelFunctionFactory factory;
    private final Conversation conversation;

    @Getter
    @Setter
    private UUID vehicleId;

    @Getter
    private final RentalCreateModel rental;

    @Inject
    public RentalCreate(ModelFunctionFactory factory, Conversation conversation, RentalCreateModel rental) {
        this.factory = factory;
        this.conversation = conversation;
        this.rental = rental;
    }

    @EJB
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @EJB
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void init() {
        if (conversation.isTransient()) {
            VehicleModel vehicle = vehicleService.find(vehicleId)
                    .map(factory.vehicleToModel())
                    .orElseThrow();

            rental.setId(UUID.randomUUID());
            rental.setVehicle(vehicle);
        }
    }

    public String cancelAction() {
        return "/rental/rental_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        VehicleModel vehicle = vehicleService.find(vehicleId)
                .map(factory.vehicleToModel())
                .orElseThrow();
        rental.setId(UUID.randomUUID());
        rental.setVehicle(vehicle);
        rentalService.createForCallerPrincipal(factory.modelToRental().apply(rental));
        return "/rental/rental_list.xhtml?faces-redirect=true";
    }
}
