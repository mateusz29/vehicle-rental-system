package org.example.vehicle.view;

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
    private final VehicleService service;
    private final ModelFunctionFactory factory;
    private final Conversation conversation;

    @Getter
    VehicleCreateModel vehicle;

    @Inject
    public VehicleCreate(VehicleService service, ModelFunctionFactory factory, Conversation conversation) {
        this.service = service;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            vehicle = VehicleCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/vehicle/vehicle_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        service.create(factory.modelToVehicle().apply(vehicle));
        conversation.end();
        return "/vehicle/vehicle_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}
