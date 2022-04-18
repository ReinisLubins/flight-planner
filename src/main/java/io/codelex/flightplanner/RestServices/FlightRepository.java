package io.codelex.flightplanner.RestServices;

import io.codelex.flightplanner.AirportAndFlight.AddFlightRequest;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {
    private List<Flight> flights = new ArrayList<>();
    private int idCounter = 1234;

    public ResponseEntity<Flight> addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = new Flight(idCounter, addFlightRequest);

        if (flight.getFrom().equals(flight.getTo()) || strangeDates(flight)) {
            return new ResponseEntity<>(flight, HttpStatus.BAD_REQUEST);
        } else if (!flightIsInList(flight)) {
            idCounter++;
            flights.add(flight);
            return new ResponseEntity<>(flight, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(flight, HttpStatus.CONFLICT);
        }
    }

    public void deleteFlight(int id) {
        for (Flight flight: flights) {
            if (flight.getId() == id) {
                flights.remove(flight);
                throw new ResponseStatusException(HttpStatus.OK);
            }
        }
    }

    public ResponseEntity<Flight> fetchFlight(int id) {
        Flight foundFlight = null;
        for (Flight flight: flights) {
            if (flight.getId() == id) {
                foundFlight = flight;
            }
        }

        if (foundFlight != null) {
            return new ResponseEntity<>(foundFlight, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public void clearFlights() {
        flights.clear();
    }

    private boolean flightIsInList(Flight flight) {
        boolean isEqual = false;
        for (Flight listFlight: flights) {
            if (flight.getFrom().equals(listFlight.getFrom()) &&
                    flight.getTo().equals(listFlight.getTo()) &&
                    flight.getCarrier().equals(listFlight.getCarrier()) &&
                    flight.getDepartureTime().equals(listFlight.getDepartureTime()) &&
                    flight.getArrivalTime().equals(listFlight.getArrivalTime())) {
                isEqual = true;
                break;
            }
        }
        return isEqual;
    }

    private boolean strangeDates(Flight flight) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime departure = LocalDateTime.parse(flight.getDepartureTime(), formatter);
        LocalDateTime arrival = LocalDateTime.parse(flight.getArrivalTime(), formatter);

        return departure.isEqual(arrival) || arrival.isBefore(departure);
    }
}
