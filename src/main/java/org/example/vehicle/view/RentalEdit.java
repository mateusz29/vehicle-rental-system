package org.example.vehicle.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalEditModel;
import org.example.vehicle.service.RentalService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class RentalEdit implements Serializable {
    private final RentalService service;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private RentalEditModel rental;

    @Inject
    public RentalEdit(RentalService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Rental> rental = service.find(id);
        if (rental.isPresent()) {
            this.rental = factory.rentalToEditModel().apply(rental.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Rental not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateRental().apply(service.find(id).orElseThrow(), rental));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
