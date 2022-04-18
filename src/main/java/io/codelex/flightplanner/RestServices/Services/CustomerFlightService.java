package io.codelex.flightplanner.RestServices.Services;

import io.codelex.flightplanner.RestServices.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerFlightService {
    private FlightRepository flightRepository;

    public CustomerFlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }


}
