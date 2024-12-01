package org.example.vehicle.view;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import lombok.Getter;
import lombok.Setter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalModel;
import org.example.vehicle.service.RentalService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class RentalView implements Serializable {
    private RentalService service;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private RentalModel rental;

    @Inject
    public RentalView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(RentalService service) {
        this.service = service;
    }

    public void init() throws IOException {
        try {
            Optional<Rental> rental = service.find(id);
            if (rental.isPresent()) {
                this.rental = factory.rentalToModel().apply(rental.get());
            } else {
                FacesContext.getCurrentInstance().getExternalContext()
                        .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Rental not found");
            }
        } catch (EJBAccessException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        }
    }
}
