package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.service.FlightService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testing-api")
public class TestingController {
    private final FlightService flightService;

    public TestingController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/clear")
    public void clear() {
        flightService.clearFlights();
    }
}
