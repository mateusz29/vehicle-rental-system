package org.example.vehicle.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.model.VehicleCreateModel;
import org.example.vehicle.service.VehicleService;

import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class VehicleCreate implements Serializable {
    private VehicleService service;
    private final ModelFunctionFactory factory;
    private final Conversation conversation;

    @Getter
    VehicleCreateModel vehicle;

    @Inject
    public VehicleCreate(ModelFunctionFactory factory, Conversation conversation) {
        this.factory = factory;
        this.conversation = conversation;
    }

    @EJB
    public void setService(VehicleService service) {
        this.service = service;
    }

    public void init() {
        if (conversation.isTransient()) {
            vehicle = VehicleCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
        }
    }

    public String cancelAction() {
        return "/vehicle/vehicle_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        service.create(factory.modelToVehicle().apply(vehicle));
        return "/vehicle/vehicle_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}
