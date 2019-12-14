package pl.rwojcikiewicz.jwtdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rwojcikiewicz.jwtdemo.model.Vehicle;
import pl.rwojcikiewicz.jwtdemo.model.VehicleNotFoundException;
import pl.rwojcikiewicz.jwtdemo.repository.VehicleRepository;

@RestController
@RequestMapping("/v1/vehicles")
public class VehicleController {

    private VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(vehicleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                vehicleRepository
                        .findById(id)
                        .orElseThrow(() -> new VehicleNotFoundException("Not found for given ID")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVehicle(@PathVariable Long id) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Not found for given ID"));

        vehicleRepository.delete(vehicle);

        return ResponseEntity.noContent().build();
    }
}
