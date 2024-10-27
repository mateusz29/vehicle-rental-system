package org.example.vehicle.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.model.RentalsModel;
import org.example.vehicle.service.RentalService;

@RequestScoped
@Named
public class RentalList {
    private final RentalService service;
    private final ModelFunctionFactory factory;

    private RentalsModel rentals;

    @Inject
    public RentalList(RentalService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public RentalsModel getRentals() {
        if (rentals == null) {
            rentals = factory.rentalsToModel().apply(service.findAll());
        }
        return rentals;
    }

    public String deleteAction(RentalsModel.Rental rental) {
        service.delete(rental.getId());
        return "rental_list?faces-redirect=true";
    }
}