package org.example.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextListener;
import lombok.SneakyThrows;
import org.example.user.entity.User;
import org.example.user.service.UserService;

import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class InitializeData implements ServletContextListener {
    private final UserService userService;

    @Inject
    public InitializeData(UserService userService) {
        this.userService = userService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
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

        User James = User.builder()
                .uuid(UUID.fromString("bbc0a717-ee54-4a36-8a49-415ecbc51289"))
                .username("Jim")
                .birthday(LocalDate.of(1980, 5, 5))
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

        userService.createUser(George);
        userService.createUser(Nicholas);
        userService.createUser(James);
        userService.createUser(Wilson);
        userService.createUser(Gregory);
    }
}
