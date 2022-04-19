package io.codelex.flightplanner.RestServices.Controllers;

import io.codelex.flightplanner.AirportAndFlight.AddFlightRequest;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import io.codelex.flightplanner.RestServices.Services.AdminFlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {
    private AdminFlightService flightService;

    public AdminFlightController(AdminFlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    public synchronized ResponseEntity<Flight> addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return flightService.addFlight(addFlightRequest);
    }

    @DeleteMapping("/flights/{id}")
    public synchronized void deleteFlight(@PathVariable int id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public synchronized ResponseEntity<Flight> fetchFlight(@PathVariable int id) {
        return flightService.fetchFlight(id);
    }
}
