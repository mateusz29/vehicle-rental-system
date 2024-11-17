package org.example.vehicle.view;

import jakarta.enterprise.context.Conversation;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.model.RentalCreateModel;
import org.example.vehicle.model.VehicleModel;
import org.example.vehicle.service.RentalService;
import org.example.vehicle.service.VehicleService;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.UUID;

@ViewScoped
@Named
public class RentalCreate implements Serializable {
    private final RentalService rentalService;
    private final VehicleService vehicleService;
    private final ModelFunctionFactory factory;
    private final Conversation conversation;

    @Getter
    @Setter
    private UUID vehicleId;

    @Getter
    RentalCreateModel rental;

    @Inject
    public RentalCreate(RentalService rentalService, VehicleService vehicleService, ModelFunctionFactory factory, Conversation conversation) {
        this.rentalService = rentalService;
        this.vehicleService = vehicleService;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            VehicleModel vehicle = vehicleService.find(vehicleId)
                    .map(factory.vehicleToModel())
                    .orElseThrow();

            rental = RentalCreateModel.builder()
                    .id(UUID.randomUUID())
                    .returned(true)
                    .rentalDate(formattedDate)
                    .returnDate(formattedDate)
                    .vehicle(vehicle)
                    .build();
        }
    }

    public String cancelAction() {
        return "/rental/rental_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        rentalService.create(factory.modelToRental().apply(rental));
        return "/rental/rental_list.xhtml?faces-redirect=true";
    }
}
