package org.example.vehicle.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.model.RentalsModel;
import org.example.vehicle.service.RentalService;

@RequestScoped
@Named
public class RentalList {
    private RentalService service;
    private final ModelFunctionFactory factory;

    private RentalsModel rentals;

    @Inject
    public RentalList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(RentalService service) {
        this.service = service;
    }

    public RentalsModel getRentals() {
        if (rentals == null) {
            rentals = factory.rentalsToModel().apply(service.findAllForCallerPrincipal());
        }
        return rentals;
    }

    public String deleteAction(RentalsModel.Rental rental) {
        service.delete(rental.getId());
        return "rental_list?faces-redirect=true";
    }
}
