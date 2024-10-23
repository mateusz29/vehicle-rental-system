package org.example.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextListener;
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
public class InitializeData implements ServletContextListener {
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
                .uuid(UUID.fromString("7553f71e-5217-4a39-9680-f506d52b08e5"))
                .username("Georgy")
                .birthday(LocalDate.of(1999, 1, 1))
                .build();

        User Nicholas = User.builder()
                .uuid(UUID.fromString("d1cdcccd-17ed-4db5-a0cd-e7c7cd5ba4b3"))
                .username("Nick")
                .birthday(LocalDate.of(1978, 10, 12))
                .build();

        User Wilson = User.builder()
                .uuid(UUID.fromString("e3cc7be8-b40c-4c3a-afc3-796880b4cccc"))
                .username("Will")
                .birthday(LocalDate.of(1990, 4, 20))
                .build();

        User Gregory = User.builder()
                .uuid(UUID.fromString("3c9ee08b-b759-47a0-8ad2-3b52827c0583"))
                .username("House")
                .birthday(LocalDate.of(1987, 2, 16))
                .build();

        userService.create(George);
        userService.create(Nicholas);
        userService.create(Wilson);
        userService.create(Gregory);

        Vehicle Porsche = Vehicle.builder()
                .uuid(UUID.fromString("e83dad4d-5d56-4065-8630-8a887536dc41"))
                .model("911")
                .brand("Porsche")
                .type(VehicleType.CAR)
                .build();

        Vehicle BMW = Vehicle.builder()
                .uuid(UUID.fromString("09d88ce5-4fed-43d1-97da-836cc8997201"))
                .model("M5")
                .brand("BMW")
                .type(VehicleType.CAR)
                .build();

        Vehicle Ducati = Vehicle.builder()
                .uuid(UUID.fromString("2afa2402-9373-428c-9206-2fc7dd1f83aa"))
                .model("Panigale")
                .brand("Ducati")
                .type(VehicleType.MOTORCYCLE)
                .build();

        Vehicle Man = Vehicle.builder()
                .uuid(UUID.fromString("00a1a742-f885-41e1-b37d-92891085b791"))
                .model("TGX")
                .brand("MAN")
                .type(VehicleType.TRUCK)
                .build();

        vehicleService.create(Porsche);
        vehicleService.create(BMW);
        vehicleService.create(Ducati);
        vehicleService.create(Man);

        Rental rental1 = Rental.builder()
                .uuid(UUID.fromString("9b26b76f-0fd7-4374-a158-0d736be07823"))
                .rentalDate(LocalDate.of(2021, 1, 1))
                .returnDate(LocalDate.of(2021, 1, 10))
                .returned(true)
                .user(George)
                .vehicle(Porsche)
                .build();

        Rental rental2 = Rental.builder()
                .uuid(UUID.fromString("9807fd8b-13d3-4663-80d2-72a3e809eaa1"))
                .rentalDate(LocalDate.of(2022, 2, 15))
                .returnDate(LocalDate.of(2022, 3, 5))
                .returned(true)
                .user(Nicholas)
                .vehicle(BMW)
                .build();

        Rental rental3 = Rental.builder()
                .uuid(UUID.fromString("1a84328f-187b-4320-a5cc-4ff949691335"))
                .rentalDate(LocalDate.of(2021, 6, 9))
                .returnDate(LocalDate.of(2021, 6, 15))
                .returned(true)
                .user(Wilson)
                .vehicle(Ducati)
                .build();

        Rental rental4 = Rental.builder()
                .uuid(UUID.fromString("88ba8e73-f9e8-4e3a-a66d-32b01ae60c30"))
                .rentalDate(LocalDate.of(2023, 5, 13))
                .returnDate(LocalDate.of(2023, 10, 20))
                .returned(true)
                .user(Gregory)
                .vehicle(Man)
                .build();

        rentalService.create(rental1);
        rentalService.create(rental2);
        rentalService.create(rental3);
        rentalService.create(rental4);

        System.out.println("Data initialized");
        printAll();

        System.out.println("Deleted");
        rentalService.delete(rental4);
        printAll();

        Vehicle motorcycle = Vehicle.builder()
                .uuid(UUID.fromString("2afa2402-9373-428c-9206-2fc7dd1f83aa"))
                .model("igkernkn")
                .brand("ndskndkl")
                .type(VehicleType.MOTORCYCLE)
                .build();

        System.out.println("Updated vehicle");
        vehicleService.update(motorcycle);
        printAll();

        User Wilson1 = User.builder()
                .uuid(UUID.fromString("e3cc7be8-b40c-4c3a-afc3-796880b4cccc"))
                .username("Wilson")
                .birthday(LocalDate.of(1990, 4, 20))
                .build();

        System.out.println("Updated user");
        userService.update(Wilson1);
        printAll();


        requestContextController.deactivate();
    }

    public void printAll() {
        System.out.println("Rentals:");
        rentalService.findAll().forEach(System.out::println);
        System.out.println("Vehicles:");
        vehicleService.findAll().forEach(System.out::println);
        System.out.println("Users:");
        userService.findAllUsers().forEach(System.out::println);
    }
}
