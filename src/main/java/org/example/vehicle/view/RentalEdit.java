package org.example.vehicle.view;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import lombok.Getter;
import lombok.Setter;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalEditModel;
import org.example.vehicle.model.VehicleModel;
import org.example.vehicle.service.RentalService;
import org.example.vehicle.service.VehicleService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class RentalEdit implements Serializable {
    private RentalService rentalService;
    private VehicleService vehicleService;
    private final ModelFunctionFactory factory;
    private final FacesContext facesContext;

    @Getter
    @Setter
    private UUID rentalId;

    @Getter
    @Setter
    private UUID vehicleId;

    @Getter
    private RentalEditModel rental;

    @Getter
    private RentalEditModel unsavedRental;

    @Inject
    public RentalEdit(ModelFunctionFactory factory, FacesContext facesContext) {
        this.facesContext = facesContext;
        this.factory = factory;
    }

    @EJB
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @EJB
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public void init() throws IOException {
        Optional<Rental> rental = rentalService.findForCallerPrincipal(rentalId);
        if (rental.isPresent()) {
            this.rental = factory.rentalToEditModel().apply(rental.get());
            VehicleModel vehicle = vehicleService.find(vehicleId)
                    .map(factory.vehicleToModel())
                    .orElseThrow();

            this.rental.setVehicle(vehicle);

        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Rental not found");
        }
    }

    public String saveAction() throws IOException{
        try {
            rentalService.update(factory.updateRental().apply(rentalService.find(rentalId).orElseThrow(), rental));
            return "/rental/rental_list.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                System.out.println("test");
                unsavedRental = rental;
                init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null ;
        }

    }
}
