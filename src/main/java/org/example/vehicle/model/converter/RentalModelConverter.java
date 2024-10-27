package org.example.vehicle.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.model.RentalModel;
import org.example.vehicle.service.RentalService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = RentalModel.class, managed = true)
public class RentalModelConverter implements Converter<RentalModel> {
    private final RentalService service;
    private final ModelFunctionFactory factory;

    @Inject
    public RentalModelConverter(RentalService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public RentalModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        Optional<Rental> rental = service.find(UUID.fromString(value));
        return rental.map(factory.rentalToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, RentalModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
