package org.example.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import org.example.user.entity.User;
import org.example.user.service.UserService;
import org.example.vehicle.entity.Rental;
import org.example.vehicle.entity.Vehicle;
import org.example.vehicle.entity.VehicleType;
import org.example.vehicle.service.RentalService;
import org.example.vehicle.service.VehicleService;

import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class InitializeData {
    private final UserService userService;
    private final VehicleService vehicleService;
    private final RentalService rentalService;
    private final RequestContextController requestContextController;

    @Inject
    public InitializeData(
            UserService userService,
            VehicleService vehicleService,
            RentalService rentalService,
            RequestContextController requestContextController
    ) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.rentalService = rentalService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate(); // start request scope in order to inject request scoped repositories

        User George = User.builder()
                .id(UUID.fromString("7553f71e-5217-4a39-9680-f506d52b08e5"))
                .username("Georgy")
                .birthday(LocalDate.of(1999, 1, 1))
                .build();

        User Nicholas = User.builder()
                .id(UUID.fromString("d1cdcccd-17ed-4db5-a0cd-e7c7cd5ba4b3"))
                .username("Nick")
                .birthday(LocalDate.of(1978, 10, 12))
                .build();

        User Wilson = User.builder()
                .id(UUID.fromString("e3cc7be8-b40c-4c3a-afc3-796880b4cccc"))
                .username("Will")
                .birthday(LocalDate.of(1990, 4, 20))
                .build();

        User Gregory = User.builder()
                .id(UUID.fromString("3c9ee08b-b759-47a0-8ad2-3b52827c0583"))
                .username("House")
                .birthday(LocalDate.of(1987, 2, 16))
                .build();

        userService.create(George);
        userService.create(Nicholas);
        userService.create(Wilson);
        userService.create(Gregory);

        Vehicle Porsche = Vehicle.builder()
                .id(UUID.fromString("e83dad4d-5d56-4065-8630-8a887536dc41"))
                .model("911")
                .brand("Porsche")
                .type(VehicleType.CAR)
                .build();

        Vehicle BMW = Vehicle.builder()
                .id(UUID.fromString("09d88ce5-4fed-43d1-97da-836cc8997201"))
                .model("M5")
                .brand("BMW")
                .type(VehicleType.CAR)
                .build();

        Vehicle Ducati = Vehicle.builder()
                .id(UUID.fromString("2afa2402-9373-428c-9206-2fc7dd1f83aa"))
                .model("Panigale")
                .brand("Ducati")
                .type(VehicleType.MOTORCYCLE)
                .build();

        Vehicle Man = Vehicle.builder()
                .id(UUID.fromString("00a1a742-f885-41e1-b37d-92891085b791"))
                .model("TGX")
                .brand("MAN")
                .type(VehicleType.TRUCK)
                .build();

        vehicleService.create(Porsche);
        vehicleService.create(BMW);
        vehicleService.create(Ducati);
        vehicleService.create(Man);

        Rental rental1 = Rental.builder()
                .id(UUID.fromString("9b26b76f-0fd7-4374-a158-0d736be07823"))
                .referenceCode("RENT-00001")
                .rentalDate(LocalDate.of(2021, 1, 1))
                .returnDate(LocalDate.of(2021, 1, 10))
                .returned(true)
                .user(George)
                .vehicle(Porsche)
                .build();

        Rental rental2 = Rental.builder()
                .id(UUID.fromString("9807fd8b-13d3-4663-80d2-72a3e809eaa1"))
                .referenceCode("RENT-00002")
                .rentalDate(LocalDate.of(2022, 2, 15))
                .returnDate(LocalDate.of(2022, 3, 5))
                .returned(true)
                .user(Nicholas)
                .vehicle(BMW)
                .build();

        Rental rental3 = Rental.builder()
                .id(UUID.fromString("1a84328f-187b-4320-a5cc-4ff949691335"))
                .referenceCode("RENT-00003")
                .rentalDate(LocalDate.of(2021, 6, 9))
                .returnDate(LocalDate.of(2021, 6, 15))
                .returned(true)
                .user(Wilson)
                .vehicle(Ducati)
                .build();

        Rental rental4 = Rental.builder()
                .id(UUID.fromString("88ba8e73-f9e8-4e3a-a66d-32b01ae60c30"))
                .referenceCode("RENT-00004")
                .rentalDate(LocalDate.of(2023, 5, 13))
                .returnDate(LocalDate.of(2023, 10, 20))
                .returned(true)
                .user(Gregory)
                .vehicle(Man)
                .build();

        Rental rental5 = Rental.builder()
                .id(UUID.fromString("7625a3cc-df02-4db6-b77f-4b1393733d2a"))
                .referenceCode("RENT-00005")
                .rentalDate(LocalDate.of(2022, 4, 9))
                .returnDate(LocalDate.of(2022, 5, 1))
                .returned(true)
                .user(Wilson)
                .vehicle(Ducati)
                .build();

        Rental rental6 = Rental.builder()
                .id(UUID.fromString("5a33ad74-504e-4234-bda8-adc36b5e55b8"))
                .referenceCode("RENT-00006")
                .rentalDate(LocalDate.of(2023, 3, 4))
                .returnDate(LocalDate.of(2023, 4, 22))
                .returned(true)
                .user(Nicholas)
                .vehicle(Man)
                .build();

        Rental rental7 = Rental.builder()
                .id(UUID.fromString("bf84d400-4400-4d14-becf-a9311901f30f"))
                .referenceCode("RENT-00007")
                .rentalDate(LocalDate.of(2022, 11, 7))
                .returnDate(LocalDate.of(2023, 5, 3))
                .returned(true)
                .user(George)
                .vehicle(Man)
                .build();

        Rental rental8 = Rental.builder()
                .id(UUID.fromString("14beed3c-69e4-4b68-b657-704b11a1f90e"))
                .referenceCode("RENT-00008")
                .rentalDate(LocalDate.of(2024, 3, 4))
                .returnDate(LocalDate.of(2024, 7, 22))
                .returned(true)
                .user(Gregory)
                .vehicle(Man)
                .build();

        rentalService.create(rental1);
        rentalService.create(rental2);
        rentalService.create(rental3);
        rentalService.create(rental4);
        rentalService.create(rental5);
        rentalService.create(rental6);
        rentalService.create(rental7);
        rentalService.create(rental8);

        requestContextController.deactivate();
    }
}
