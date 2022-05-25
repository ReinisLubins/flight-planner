package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import io.codelex.flightplanner.response.PageResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
public class FlightServiceInMemory extends AbstractFlightService implements FlightService {
    private final FlightInMemoryRepository flightRepository;

    public FlightServiceInMemory(FlightInMemoryRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = new Flight(addFlightRequest);

        if (isTheSameFlightFromTo(flight) || isInvalidDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return flightRepository.addFlight(flight);
    }

    public void deleteFlight(long id) {
        flightRepository.deleteFlight(id);
    }

    public List<Airport> searchAirports(String search) {
        return flightRepository.searchAirports(search);
    }

    public Flight findFlightById(long id) {
        return flightRepository.findFlightById(id);
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        if (isTheSameSearchFlightFromTo(searchFlightsRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return flightRepository.searchFlights(searchFlightsRequest);
    }

    public void clearFlights() {
        flightRepository.clearFlights();
    }
}
