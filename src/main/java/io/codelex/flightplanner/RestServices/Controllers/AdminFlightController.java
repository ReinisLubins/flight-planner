package io.codelex.flightplanner.RestServices.Controllers;

import io.codelex.flightplanner.AirportAndFlight.AddFlightRequest;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import io.codelex.flightplanner.RestServices.Services.AdminFlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {
    private AdminFlightService flightService;

    public AdminFlightController(AdminFlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return flightService.addFlight(addFlightRequest);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable int id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> fetchFlight(@PathVariable int id) {
        return flightService.fetchFlight(id);
    }
}
