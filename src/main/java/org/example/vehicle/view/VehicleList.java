package org.example.vehicle.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.model.VehiclesModel;
import org.example.vehicle.service.VehicleService;

import java.io.Serializable;

@ViewScoped
@Named
public class VehicleList implements Serializable {
    private final VehicleService service;
    private final ModelFunctionFactory factory;

    private VehiclesModel vehicles;

    @Inject
    public VehicleList(VehicleService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public VehiclesModel getVehicles() {
        if (vehicles == null) {
            vehicles = factory.vehiclesToModel().apply(service.findAll());
        }
        return vehicles;
    }

    public String deleteAction(VehiclesModel.Vehicle vehicle) {
        service.delete(vehicle.getId());
        return "vehicle_list?faces-redirect=true";
    }
}
