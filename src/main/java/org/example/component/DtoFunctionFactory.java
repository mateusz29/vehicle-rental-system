package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.user.dto.function.RequestToUserFunction;
import org.example.user.dto.function.UpdateUserWithRequestFunction;
import org.example.user.dto.function.UserToResponeFunction;
import org.example.user.dto.function.UsersToResponseFunction;
import org.example.vehicle.dto.function.*;

@ApplicationScoped
public class DtoFunctionFactory {

    //user
    public UserToResponeFunction userToResponse() {
        return new UserToResponeFunction();
    }
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }

    //vehicle
    public VehicleToResponseFunction vehicleToResponse() {
        return new VehicleToResponseFunction();
    }
    public VehiclesToResponseFunction vehiclesToResponse() {
        return new VehiclesToResponseFunction();
    }
    public RequestToVehicleFunction requestToVehicle() {
        return new RequestToVehicleFunction();
    }
    public UpdateVehicleWithRequestFunction updateVehicle() {
        return new UpdateVehicleWithRequestFunction();
    }

    //rental
    public RentalToResponseFunction rentalToResponse() {
        return new RentalToResponseFunction();
    }
    public RentalsToResponseFunction rentalsToResponse() {
        return new RentalsToResponseFunction();
    }
    public RequestToRentalFunction requestToRental() {
        return new RequestToRentalFunction();
    }
    public UpdateRentalWithRequestFunction updateRental() {
        return new UpdateRentalWithRequestFunction();
    }
}
