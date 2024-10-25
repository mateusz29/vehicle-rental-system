package org.example.vehicle.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.component.DtoFunctionFactory;
import org.example.controller.servlet.exception.NotFoundException;
import org.example.vehicle.controller.api.VehicleController;
import org.example.vehicle.dto.GetVehicleResponse;
import org.example.vehicle.dto.GetVehiclesResponse;
import org.example.vehicle.dto.PutVehicleRequest;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.service.VehicleService;

import java.util.UUID;

@RequestScoped
public class VehicleSimpleController implements VehicleController {
    private final VehicleService vehicleService;
    private final DtoFunctionFactory factory;

    @Inject
    public VehicleSimpleController(VehicleService vehicleService, DtoFunctionFactory factory) {
        this.vehicleService = vehicleService;
        this.factory = factory;
    }

    @Override
    public GetVehicleResponse getVehicle(UUID uuid) {
        return vehicleService.find(uuid)
                .map(factory.vehicleToResponseFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetVehiclesResponse getVehicles() {
        return factory.vehiclesToResponseFunction().apply(vehicleService.findAll());
    }

    @Override
    public void deleteVehicle(UUID uuid) {
        vehicleService.find(uuid).ifPresentOrElse(vehicleService::delete, () -> {
            throw new NotFoundException();
        });
    }

    @Override
    public void putVehicle(UUID uuid, PutVehicleRequest request) {
        Vehicle vehicle = factory.requestToVehicleFunction().apply(uuid, request);

        if (vehicleService.find(vehicle.getUuid()).isPresent()) {
            vehicleService.update(vehicle);
        } else {
            vehicleService.create(vehicle);
        }
    }
}
