package org.example.vehicle.domain;

import java.time.LocalDate;

public interface StatsHolder {
    LocalDate getRentalDate();
    LocalDate getReturnDate();
}
