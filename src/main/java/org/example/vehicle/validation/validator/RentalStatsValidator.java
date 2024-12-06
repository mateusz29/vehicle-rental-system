package org.example.vehicle.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.vehicle.domain.StatsHolder;
import org.example.vehicle.validation.binding.ValidRentalStats;

import java.time.temporal.ChronoUnit;

public class RentalStatsValidator implements ConstraintValidator<ValidRentalStats, StatsHolder> {
    private int maxRentalDays;

    @Override
    public void initialize(ValidRentalStats constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        maxRentalDays = constraintAnnotation.maxRentalDays();
    }

    @Override
    public boolean isValid(StatsHolder value, ConstraintValidatorContext context) {
        return ChronoUnit.DAYS.between(value.getRentalDate(), value.getReturnDate()) <= maxRentalDays;
    }
}
