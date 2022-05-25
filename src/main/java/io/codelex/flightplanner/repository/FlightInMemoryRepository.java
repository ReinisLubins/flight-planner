package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import io.codelex.flightplanner.response.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightInMemoryRepository {
    private final List<Flight> flights = new ArrayList<>();
    private long idCounter = 1234L;

    public void clearFlights() {
        flights.clear();
    }

    public synchronized Flight addFlight(Flight flight) {
        if (!flightIsInList(flight)) {
            flight.setId(idCounter);
            idCounter++;
            flights.add(flight);
            return flight;
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public synchronized void deleteFlight(long id) {
        for (Flight flight : flights) {
            if (flight.getId() == id) {
                flights.remove(flight);
                throw new ResponseStatusException(HttpStatus.OK);
            }
        }
    }

    public Flight findFlightById(long id) {
        return flights
                .stream()
                .filter(flight -> flight.getId() == id).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Airport> searchAirports(String search) {
        String searchPhrase = formatForSearch(search);

        return flights.stream()
                .map(Flight::getFrom)
                .filter(airport ->
                        formatForSearch(airport.getAirport()).contains(searchPhrase)
                                || formatForSearch(airport.getCity()).contains(searchPhrase)
                                || formatForSearch(airport.getCountry()).contains(searchPhrase))
                .toList();
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        List<Flight> foundFlights = searchedFlightsFound(searchFlightsRequest);
        return new PageResult<>(0, foundFlights.size(), foundFlights);
    }

    private String formatForSearch(String text) {
        return text.toLowerCase().replaceAll("\\s+", "");
    }

    private boolean flightIsInList(Flight flight) {
        return flights.stream()
                .anyMatch(listFlight -> flight.getFrom().equals(listFlight.getFrom())
                && flight.getTo().equals(listFlight.getTo())
                && flight.getCarrier().equals(listFlight.getCarrier())
                && flight.getDepartureTime().equals(listFlight.getDepartureTime())
                && flight.getArrivalTime().equals(listFlight.getArrivalTime()));

    }

    private List<Flight> searchedFlightsFound(SearchFlightsRequest searchFlightsRequest) {
        List<Flight> foundFlights = new ArrayList<>();

        for (Flight listFlight : flights) {
            if (listFlight.searchedFlightsAreEqual(searchFlightsRequest)) {
                foundFlights.add(listFlight);
            }
        }
        return foundFlights;
    }
}
