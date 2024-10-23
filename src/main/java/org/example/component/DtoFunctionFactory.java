package org.example.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.user.dto.function.RequestToUserFunction;
import org.example.user.dto.function.UserToResponeFunction;
import org.example.user.dto.function.UsersToResponseFunction;
import org.example.vehicle.dto.function.*;

@ApplicationScoped
public class DtoFunctionFactory {

    //user
    public UserToResponeFunction userToResponeFunction() {
        return new UserToResponeFunction();
    }

    public UsersToResponseFunction usersToResponseFunction() {
        return new UsersToResponseFunction();
    }

    public RequestToUserFunction requestToUserFunction() {
        return new RequestToUserFunction();
    }

    //vehicle
    public VehicleToResponseFunction vehicleToResponseFunction() {
        return new VehicleToResponseFunction();
    }

    public VehiclesToResponseFunction vehiclesToResponseFunction() {
        return new VehiclesToResponseFunction();
    }

    public RequestToVehicleFunction requestToVehicleFunction() {
        return new RequestToVehicleFunction();
    }

    //rental
    public RentalToResponseFunction rentalToResponseFunction() {
        return new RentalToResponseFunction();
    }

    public RentalsToResponseFunction rentalsToResponseFunction() {
        return new RentalsToResponseFunction();
    }

    public RequestToRentalFunction requestToRentalFunction() {
        return new RequestToRentalFunction();
    }
}
