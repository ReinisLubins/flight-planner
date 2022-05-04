package io.codelex.flightplanner.RestServices.Controllers;

import io.codelex.flightplanner.AirportAndFlight.AddFlightRequest;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import io.codelex.flightplanner.RestServices.Services.AdminFlightService;
import org.springframework.http.HttpStatus;
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
        return flightService.fetchFlight(id);
    }
}
