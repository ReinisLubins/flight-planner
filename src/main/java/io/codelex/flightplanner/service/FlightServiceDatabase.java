package io.codelex.flightplanner.service;

import io.codelex.flightplanner.repository.AirportDatabaseRepository;
import io.codelex.flightplanner.repository.FlightDatabaseRepository;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.response.PageResult;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public class FlightServiceDatabase extends AbstractFlightService implements FlightService {
    private final FlightDatabaseRepository flightDatabaseRepository;
    private final AirportDatabaseRepository airportDatabaseRepository;

    public FlightServiceDatabase(FlightDatabaseRepository flightDatabaseRepository, AirportDatabaseRepository airportDatabaseRepository) {
        this.flightDatabaseRepository = flightDatabaseRepository;
        this.airportDatabaseRepository = airportDatabaseRepository;
    }

    public void clearFlights() {
        flightDatabaseRepository.deleteAll();
    }

    public Flight addFlight(AddFlightRequest addFlightRequest) {

        Flight flight = new Flight(addFlightRequest);

        if (isTheSameFlightFromTo(flight) || isInvalidDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (flightDatabaseRepository.findFlight(flight.getFrom(), flight.getTo(), flight.getCarrier(),
                flight.getDepartureTime(), flight.getArrivalTime()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            flight.setFrom(findOrCreate(flight.getFrom()));
            flight.setTo(findOrCreate(flight.getTo()));

            return flightDatabaseRepository.save(flight);
        }
    }

    public void deleteFlight(long id) {
        flightDatabaseRepository.findById(id).ifPresent(flightDatabaseRepository::delete);
    }

    public List<Airport> searchAirports(String search) {
        String searchPhrase = formatForSearch(search);

        return airportDatabaseRepository.searchAirportBySearchPhrase(searchPhrase);
    }

    public Flight findFlightById(long id) {
        return flightDatabaseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        List<Flight> foundFlights = searchedFlightsFound(searchFlightsRequest);
        return new PageResult<>(0, foundFlights.size(), foundFlights);
    }

    private Airport findOrCreate(Airport airport) {
        Optional<Airport> existingAirport = airportDatabaseRepository.findById(airport.getAirport());
        return existingAirport.orElseGet(() -> airportDatabaseRepository.save(airport));
    }

    private String formatForSearch(String text) {
        return text.toLowerCase().replaceAll("\\s+", "");
    }

    private List<Flight> searchedFlightsFound(SearchFlightsRequest searchFlightsRequest) {
        if (isTheSameSearchFlightFromTo(searchFlightsRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return flightDatabaseRepository.findFlights(
                searchFlightsRequest.getFrom(),
                searchFlightsRequest.getTo(),
                searchFlightsRequest.getDepartureDate().atStartOfDay());
    }
}
