package org.example.vehicle.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.VehicleEditModel;
import org.example.vehicle.service.VehicleService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class VehicleEdit implements Serializable {
    private VehicleService service;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private VehicleEditModel vehicle;

    @Inject
    public VehicleEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(VehicleService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Vehicle> vehicle = service.find(id);
        if (vehicle.isPresent()) {
            this.vehicle = factory.vehicleToEditModel().apply(vehicle.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Vehicle not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateVehicle().apply(service.find(id).orElseThrow(), vehicle));
        return  "/vehicle/vehicle_list.xhtml?faces-redirect=true";
    }
}
