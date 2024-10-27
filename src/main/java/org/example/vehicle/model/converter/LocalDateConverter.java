package org.example.vehicle.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@FacesConverter("localDateConverter")
public class LocalDateConverter implements Converter<LocalDate> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate getAsObject(FacesContext context, UIComponent component,String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return LocalDate.parse(value, FORMATTER);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocalDate value) {
        return value == null ? "" : value.format(FORMATTER);
    }
}
