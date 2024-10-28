package org.example.vehicle.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import org.example.component.ModelFunctionFactory;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.model.VehicleModel;
import org.example.vehicle.service.VehicleService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(value = "vehicleModelConverter", managed = true)
public class VehicleModelConverter implements Converter<VehicleModel> {
    private final VehicleService service;
    private final ModelFunctionFactory factory;

    @Inject
    public VehicleModelConverter(VehicleService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public VehicleModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Vehicle> vehicle = service.find(UUID.fromString(value));
        return vehicle.map(factory.vehicleToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, VehicleModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
