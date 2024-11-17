package org.example.vehicle.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalEditModel;
import org.example.vehicle.model.VehicleModel;
import org.example.vehicle.service.RentalService;
import org.example.vehicle.service.VehicleService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class RentalEdit implements Serializable {
    private final RentalService rentalService;
    private final VehicleService vehicleService;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID rentalId;

    @Getter
    @Setter
    private UUID vehicleId;

    @Getter
    private RentalEditModel rental;

    @Inject
    public RentalEdit(RentalService rentalService, VehicleService vehicleService, ModelFunctionFactory factory) {
        this.rentalService = rentalService;
        this.vehicleService = vehicleService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Rental> rental = rentalService.find(rentalId);
        if (rental.isPresent()) {
            this.rental = factory.rentalToEditModel().apply(rental.get());
            VehicleModel vehicle = vehicleService.find(vehicleId)
                    .map(factory.vehicleToModel())
                    .orElseThrow();

            this.rental.setVehicle(vehicle);

        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Rental not found");
        }
    }

    public String saveAction() {
        rentalService.update(factory.updateRental().apply(rentalService.find(rentalId).orElseThrow(), rental));
        return "/rental/rental_list.xhtml?faces-redirect=true";
    }
}
