package org.example.vehicle.validation.binding;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.vehicle.validation.validator.RentalStatsValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RentalStatsValidator.class)
@Documented
public @interface ValidRentalStats {
    String message() default "the rental can only be rented for maximum of {maxRentalDays} days";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int maxRentalDays() default 14;
}
