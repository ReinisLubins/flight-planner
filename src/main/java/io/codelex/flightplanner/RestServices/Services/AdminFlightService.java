package io.codelex.flightplanner.RestServices.Services;

import io.codelex.flightplanner.AirportAndFlight.AddFlightRequest;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import io.codelex.flightplanner.RestServices.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminFlightService {
    private FlightRepository flightRepository;

    public AdminFlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized Flight addFlight(AddFlightRequest addFlightRequest) {
        return flightRepository.addFlight(addFlightRequest);
    }

    public synchronized void deleteFlight(int id) {
        flightRepository.deleteFlight(id);
    }

    public synchronized Flight fetchFlight(int id) {
        return flightRepository.fetchFlight(id);
    }

}
