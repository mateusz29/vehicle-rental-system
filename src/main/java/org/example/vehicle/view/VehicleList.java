package org.example.vehicle.view;

import jakarta.ejb.EJB;
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
    private VehicleService service;
    private final ModelFunctionFactory factory;

    private VehiclesModel vehicles;

    @Inject
    public VehicleList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(VehicleService service) {
        this.service = service;
    }

    public VehiclesModel getVehicles() {
        if (vehicles == null) {
            vehicles = factory.vehiclesToModel().apply(service.findAll());
        }
        return vehicles;
    }

    public void deleteAction(VehiclesModel.Vehicle vehicle) {
        service.delete(vehicle.getId());
        vehicles = null;
    }
}
