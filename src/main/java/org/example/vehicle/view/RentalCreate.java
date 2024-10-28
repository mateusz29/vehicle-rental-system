package org.example.vehicle.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.model.RentalCreateModel;
import org.example.vehicle.model.VehicleModel;
import org.example.vehicle.service.RentalService;
import org.example.vehicle.service.VehicleService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
public class RentalCreate implements Serializable {
    private final RentalService rentalService;
    private final VehicleService vehicleService;
    private final ModelFunctionFactory factory;
    private final Conversation conversation;

    @Getter
    RentalCreateModel rental;

    @Getter
    private List<VehicleModel> vehicles;

    @Inject
    public RentalCreate(RentalService rentalService, VehicleService vehicleService, ModelFunctionFactory factory, Conversation conversation) {
        this.rentalService = rentalService;
        this.vehicleService = vehicleService;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            vehicles = vehicleService.findAll().stream()
                    .map(factory.vehicleToModel())
                    .collect(Collectors.toList());
            rental = RentalCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/rental/rental_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        System.out.println("TESTUBGDUGBSDGSD dsfdsb");
        System.out.println("Rental created: " + rental);
        rentalService.create(factory.modelToRental().apply(rental));
        conversation.end();
        return "/rental/rental_list.xhtml?faces-redirect=true";
    }
}
