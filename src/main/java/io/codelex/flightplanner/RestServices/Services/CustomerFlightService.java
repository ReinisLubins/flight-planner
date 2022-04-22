package io.codelex.flightplanner.RestServices.Services;

import io.codelex.flightplanner.AirportAndFlight.Airport;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import io.codelex.flightplanner.AirportAndFlight.PageResult;
import io.codelex.flightplanner.AirportAndFlight.SearchFlightsRequest;
import io.codelex.flightplanner.RestServices.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFlightService {
    private FlightRepository flightRepository;

    public CustomerFlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }

    public Flight findFlightById(int id) {
        return flightRepository.fetchFlight(id);
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        return flightRepository.searchFlights(searchFlightsRequest);
    }
}
