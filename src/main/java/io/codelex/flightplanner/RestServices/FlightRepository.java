package io.codelex.flightplanner.RestServices;

import io.codelex.flightplanner.AirportAndFlight.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {
    private List<Flight> flights = new ArrayList<>();
    private int idCounter = 1234;

    public void clearFlights() {
        flights.clear();
    }

    public synchronized Flight addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = new Flight(idCounter, addFlightRequest);

        if (flight.getFrom().equals(flight.getTo()) || invalidDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (!flightIsInList(flight)) {
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

    public Flight fetchFlight(long id) {
        return flights
                .stream()
                .filter((Flight flight) -> flight.getId() == id).findFirst()
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
        boolean isEqual = false;
        for (Flight listFlight : flights) {
            if (flight.getFrom().equals(listFlight.getFrom())
                    && flight.getTo().equals(listFlight.getTo())
                    && flight.getCarrier().equals(listFlight.getCarrier())
                    && flight.getDepartureTime().equals(listFlight.getDepartureTime())
                    && flight.getArrivalTime().equals(listFlight.getArrivalTime())) {
                isEqual = true;
                break;
            }
        }
        return isEqual;
    }

    private List<Flight> searchedFlightsFound(SearchFlightsRequest searchFlightsRequest) {
        List<Flight> foundFlights = new ArrayList<>();

        if (searchFlightsRequest.getFrom().equals(searchFlightsRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        for (Flight listFlight : flights) {
            if (Flight.searchedFlightsAreEqual(searchFlightsRequest, listFlight)) {
                foundFlights.add(listFlight);
            }
        }
        return foundFlights;
    }

    private boolean invalidDates(Flight flight) {
        LocalDateTime departure = flight.getDepartureTime();
        LocalDateTime arrival = flight.getArrivalTime();

        return departure.isEqual(arrival) || arrival.isBefore(departure);
    }
}
