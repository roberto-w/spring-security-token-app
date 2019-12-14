package pl.rwojcikiewicz.jwtdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.rwojcikiewicz.jwtdemo.model.User;
import pl.rwojcikiewicz.jwtdemo.model.Vehicle;
import pl.rwojcikiewicz.jwtdemo.repository.UserRepository;
import pl.rwojcikiewicz.jwtdemo.repository.VehicleRepository;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.debug("Initializing sample data...");

        initUsersAndRoles();

        Arrays.asList("Car", "Bike").forEach(
                vehicleName ->
                        vehicleRepository.saveAndFlush(
                                Vehicle.builder()
                                        .name(vehicleName)
                                        .build()));

        vehicleRepository.findAll()
                .forEach(vehicle -> log.info("Car ID: " + vehicle.getId()));
    }

    private void initUsersAndRoles() {
        this.userRepository.save(
                User.builder()
                        .username("user")
                        .password(this.passwordEncoder.encode("password"))
                        .roles(Arrays.asList("ROLE_USER"))
                        .build());

        this.userRepository.save(
                User.builder()
                        .username("admin")
                        .password(this.passwordEncoder.encode("password"))
                        .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                        .build());
    }
}
