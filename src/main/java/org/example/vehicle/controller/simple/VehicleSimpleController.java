package org.example.vehicle.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.example.component.DtoFunctionFactory;
import org.example.controller.servlet.exception.BadRequestException;
import org.example.controller.servlet.exception.NotFoundException;
import org.example.vehicle.controller.api.VehicleController;
import org.example.vehicle.dto.GetVehicleResponse;
import org.example.vehicle.dto.GetVehiclesResponse;
import org.example.vehicle.dto.PatchVehicleRequest;
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
    public GetVehicleResponse getVehicle(UUID id) {
        return vehicleService.find(id)
                .map(factory.vehicleToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetVehiclesResponse getVehicles() {
        return factory.vehiclesToResponse().apply(vehicleService.findAll());
    }

    @Override
    public void deleteVehicle(UUID id) {
        vehicleService.find(id).ifPresentOrElse(
                vehicle -> vehicleService.delete(id),
                () -> {
                throw new NotFoundException();
                }
        );
    }

    @Override
    public void putVehicle(UUID id, PutVehicleRequest request) {
        try {
            vehicleService.create(factory.requestToVehicle().apply(id, request));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void updateVehicle(UUID id, PatchVehicleRequest request) {
        vehicleService.find(id).ifPresentOrElse(
                vehicle -> vehicleService.update(factory.updateVehicle().apply(vehicle, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
