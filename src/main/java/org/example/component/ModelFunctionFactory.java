package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.user.model.function.UserToModelFunction;
import org.example.user.model.function.UsersToModelFunction;
import org.example.vehicle.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {
    //vehicle
    public VehicleToModelFunction vehicleToModel() {
        return new VehicleToModelFunction();
    }
    public VehiclesToModelFunction vehiclesToModel() {
        return new VehiclesToModelFunction();
    }
    public ModelToVehicleFunction modelToVehicle() {
        return new ModelToVehicleFunction();
    }
    public VehicleToEditModelFunction vehicleToEditModel() {
        return new VehicleToEditModelFunction();
    }
    public UpdateVehicleWithModelFunction updateVehicle() {
        return new UpdateVehicleWithModelFunction();
    }

    //rental
    public RentalToModelFunction rentalToModel() {
        return new RentalToModelFunction();
    }
    public RentalsToModelFunction rentalsToModel() {
        return new RentalsToModelFunction();
    }
    public ModelToRentalFunction modelToRental() {
        return new ModelToRentalFunction();
    }
    public RentalToEditModelFunction rentalToEditModel() {
        return new RentalToEditModelFunction();
    }
    public UpdateRentalWithModelFunction updateRental() {
        return new UpdateRentalWithModelFunction();
    }

    //user
    public UserToModelFunction userToModel() {
        return new UserToModelFunction();
    }
    public UsersToModelFunction usersToModel() {
        return new UsersToModelFunction();
    }
}
