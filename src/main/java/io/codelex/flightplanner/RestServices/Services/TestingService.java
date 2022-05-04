package io.codelex.flightplanner.RestServices.Services;

import io.codelex.flightplanner.RestServices.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class TestingService {
    private FlightRepository flightRepository;

    public TestingService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void clearFlights() {
        flightRepository.clearFlights();
    }
}
