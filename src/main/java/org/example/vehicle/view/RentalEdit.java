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
    private UUID id;

    @Getter
    private RentalEditModel rental;

    @Getter
    private List<VehicleModel> vehicles;

    @Inject
    public RentalEdit(RentalService rentalService, VehicleService vehicleService, ModelFunctionFactory factory) {
        this.rentalService = rentalService;
        this.vehicleService = vehicleService;
        this.factory = factory;
    }

    public void init() throws IOException {
        // TODO: Change 2nd id to vehicleId
        Optional<Rental> rental = rentalService.findByVehicle(id, id);
        if (rental.isPresent()) {
            vehicles = vehicleService.findAll().stream()
                    .map(factory.vehicleToModel())
                    .collect(Collectors.toList());
            this.rental = factory.rentalToEditModel().apply(rental.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Rental not found");
        }
    }

    public String saveAction() {
        // TODO: Change 1st id to vehicleId
        rentalService.update(factory.updateRental().apply(rentalService.findByVehicle(id, id).orElseThrow(), rental));
        return "/rental/rental_list.xhtml?faces-redirect=true";
    }
}
