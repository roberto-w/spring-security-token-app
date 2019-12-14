package pl.rwojcikiewicz.jwtdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pl.rwojcikiewicz.jwtdemo.model.VehicleNotFoundException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    public ResponseEntity vehicleNotFound(VehicleNotFoundException e, WebRequest webRequest) {
        log.debug("Handling VehicleNotFoundException...");

        return ResponseEntity.notFound().build();
    }
}
