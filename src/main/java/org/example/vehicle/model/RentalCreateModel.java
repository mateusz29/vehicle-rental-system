package org.example.vehicle.model;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.utils.DateUtils;
import org.example.vehicle.domain.StatsHolder;
import org.example.vehicle.validation.binding.ValidRentalStats;
import org.example.vehicle.validation.group.RentalModelGroup;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@ValidRentalStats(groups = RentalModelGroup.class)
@Named
@RequestScoped
public class RentalCreateModel implements StatsHolder, Serializable {
    private UUID id;

    @NotBlank
    private String referenceCode;

    @NotNull
    private LocalDate rentalDate;

    @NotNull
    private LocalDate returnDate;

    @NotNull
    private boolean returned;

    @NotNull
    private VehicleModel vehicle;
}
