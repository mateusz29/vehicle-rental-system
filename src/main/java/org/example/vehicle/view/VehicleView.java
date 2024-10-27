package org.example.vehicle.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.RentalsModel;
import org.example.vehicle.model.VehicleModel;
import org.example.vehicle.service.RentalService;
import org.example.vehicle.service.VehicleService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class VehicleView implements Serializable {
    private final VehicleService vehicleService;
    private final RentalService rentalService;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private VehicleModel vehicle;

    @Getter
    private RentalsModel rentals;

    @Inject
    public VehicleView(VehicleService vehicleService, RentalService rentalService, ModelFunctionFactory factory) {
        this.vehicleService = vehicleService;
        this.rentalService = rentalService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Vehicle> vehicle = vehicleService.find(id);
        if (vehicle.isPresent()) {
            this.vehicle = factory.vehicleToModel().apply(vehicle.get());
            this.rentals = factory.rentalsToModel().apply(rentalService.findAllByVehicle(id).get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
        }
    }

    public String deleteRental(RentalsModel.Rental rental) {
        rentalService.delete(rental.getId());
        return "vehicle_view?faces-redirect=true&id=" + this.id;
    }
}
