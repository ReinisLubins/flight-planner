package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {
    private final FlightService flightService;

    public AdminFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return flightService.addFlight(addFlightRequest);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable long id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public Flight fetchFlight(@PathVariable long id) {
        return flightService.findFlightById(id);
    }
}
