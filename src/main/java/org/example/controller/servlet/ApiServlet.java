package org.example.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.user.avatar.controller.api.AvatarController;
import org.example.user.controller.api.UserController;
import org.example.user.dto.PutUserRequest;
import org.example.vehicle.controller.api.RentalController;
import org.example.vehicle.controller.api.VehicleController;
import org.example.vehicle.dto.PutRentalRequest;
import org.example.vehicle.dto.PutVehicleRequest;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.UUID;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private final UserController userController;
    private final AvatarController avatarController;
    private final RentalController rentalController;
    private final VehicleController vehicleController;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        public static final Pattern USERS = Pattern.compile("/users/?");
        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));
        public static final Pattern AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));
        public static final Pattern RENTALS = Pattern.compile("/rentals/?");
        public static final Pattern RENTAL = Pattern.compile("/rentals/(%s)".formatted(UUID.pattern()));
        public static final Pattern VEHICLES = Pattern.compile("/vehicles/?");
        public static final Pattern VEHICLE = Pattern.compile("/vehicles/(%s)".formatted(UUID.pattern()));

        public static final Pattern RENTALS_BY_USER = Pattern.compile("/users/(%s)/rentals".formatted(UUID.pattern()));
        public static final Pattern RENTALS_BY_VEHICLE = Pattern.compile("/vehicles/(%s)/rentals".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(UserController userController, AvatarController avatarController, RentalController rentalController, VehicleController vehicleController) {
        this.userController = userController;
        this.avatarController = avatarController;
        this.rentalController = rentalController;
        this.vehicleController = vehicleController;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RENTALS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(rentalController.getRentals()));
                return;
            } else if (path.matches(Patterns.RENTAL.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.RENTAL, path);
                response.getWriter().write(jsonb.toJson(rentalController.getRental(uuid)));
                return;
            } else if (path.matches(Patterns.RENTALS_BY_USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.RENTALS_BY_USER, path);
                response.getWriter().write(jsonb.toJson(rentalController.getUserRentals(uuid)));
                return;
            } else if (path.matches(Patterns.RENTALS_BY_VEHICLE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.RENTALS_BY_VEHICLE, path);
                response.getWriter().write(jsonb.toJson(rentalController.getVehicleRentals(uuid)));
                return;
            } else if (path.matches(Patterns.VEHICLES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(vehicleController.getVehicles()));
                return;
            } else if (path.matches(Patterns.VEHICLE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.VEHICLE, path);
                response.getWriter().write(jsonb.toJson(vehicleController.getVehicle(uuid)));
                return;
            } else if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            } else if (path.matches(Patterns.AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.AVATAR, path);
                byte[] avatar = avatarController.getAvatar(uuid);
                response.setContentLength(avatar.length);
                response.getOutputStream().write(avatar);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RENTAL.pattern())) {
                UUID uuid = extractUuid(Patterns.RENTAL, path);
                rentalController.deleteRental(uuid);
                return;
            } else if (path.matches(Patterns.VEHICLE.pattern())) {
                UUID uuid = extractUuid(Patterns.VEHICLE, path);
                vehicleController.deleteVehicle(uuid);
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.deleteUser(uuid);
                return;
            } else if (path.matches(Patterns.AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.AVATAR, path);
                avatarController.deleteAvatar(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RENTAL.pattern())) {
                UUID uuid = extractUuid(Patterns.RENTAL, path);
                PutRentalRequest putRentalRequest = jsonb.fromJson(request.getReader(), PutRentalRequest.class);
                putRentalRequest.setUuid(uuid);
                rentalController.putRental(putRentalRequest);
                response.addHeader("Location", createUrl(request, Paths.API + "rentals" + uuid.toString()));
                return;
            } else if (path.matches(Patterns.VEHICLE.pattern())) {
                UUID uuid = extractUuid(Patterns.VEHICLE, path);
                PutVehicleRequest putVehicleRequest = jsonb.fromJson(request.getReader(), PutVehicleRequest.class);
                putVehicleRequest.setUuid(uuid);
                vehicleController.putVehicle(putVehicleRequest);
                response.addHeader("Location", createUrl(request, Paths.API + "vehicles" + uuid.toString()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                PutUserRequest putUserRequest = jsonb.fromJson(request.getReader(), PutUserRequest.class);
                putUserRequest.setUuid(uuid);
                userController.putUser(putUserRequest);
                response.addHeader("Location", createUrl(request, Paths.API + "users" + uuid.toString()));
                return;
            } else if (path.matches(Patterns.AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.AVATAR, path);
                avatarController.putAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }
}
