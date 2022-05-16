package io.codelex.flightplanner.RestServices.Controllers;

import io.codelex.flightplanner.AirportAndFlight.Airport;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import io.codelex.flightplanner.AirportAndFlight.PageResult;
import io.codelex.flightplanner.AirportAndFlight.SearchFlightsRequest;
import io.codelex.flightplanner.DatabaseServices.AirportDatabaseService;
import io.codelex.flightplanner.RestServices.Services.CustomerFlightService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerFlightController {
    private CustomerFlightService customerFlightService;
    private AirportDatabaseService airportDatabaseService;

    public CustomerFlightController(CustomerFlightService customerFlightService, AirportDatabaseService airportDatabaseService) {
        this.customerFlightService = customerFlightService;
        this.airportDatabaseService = airportDatabaseService;
    }

    @GetMapping("/airports")
    public List<Airport> searchAirports(@PathParam("search") String search) {
        return customerFlightService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest searchFlightsRequest) {
        return customerFlightService.searchFlights(searchFlightsRequest);
    }

    @GetMapping("flights/{id}")
    public Flight findFlightById(@PathVariable long id) {
        return customerFlightService.findFlightById(id);
    }

    @GetMapping("/add-airport-test")
    public void addAirport() {
        airportDatabaseService.addAirport(new Airport("Latvia", "Riga", "RIX"));
    }
}
