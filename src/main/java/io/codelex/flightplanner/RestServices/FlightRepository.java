package io.codelex.flightplanner.RestServices;

import io.codelex.flightplanner.AirportAndFlight.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {
    private List<Flight> flights = new ArrayList<>();
    private int idCounter = 1234;

    public void clearFlights() {
        flights.clear();
    }

    public synchronized ResponseEntity<Flight> addFlight(AddFlightRequest addFlightRequest) {
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

    public synchronized void deleteFlight(int id) {
        for (Flight flight : flights) {
            if (flight.getId() == id) {
                flights.remove(flight);
                throw new ResponseStatusException(HttpStatus.OK);
            }
        }
    }

    public synchronized ResponseEntity<Flight> fetchFlight(int id) {
        Flight foundFlight = null;
        for (Flight flight : flights) {
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

    public List<Airport> searchAirports(String search) {
        String searchPhrase = search.toLowerCase().replaceAll("\\s+", "");
        List<Airport> airports = new ArrayList<>();

        for (Flight flight : flights) {
            String airportCountry = flight.getFrom().getCountry().toLowerCase().replaceAll("\\s+", "").toLowerCase();
            String airportCity = flight.getFrom().getCity().toLowerCase().replaceAll("\\s+", "").toLowerCase();
            String airportName = flight.getFrom().getAirport().toLowerCase().replaceAll("\\s+", "").toLowerCase();

            if (airportCountry.contains(searchPhrase)
                    || airportCity.contains(searchPhrase)
                    || airportName.contains(searchPhrase)) {
                airports.add(flight.getFrom());
            }
        }
        return airports;
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        List<Flight> foundFlights = searchedFlightsFound(searchFlightsRequest);
        return new PageResult<>(0, foundFlights.size(), foundFlights);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchFlightDeparture = LocalDate.parse(searchFlightsRequest.getDepartureDate(), formatter);
        List<Flight> foundFlights = new ArrayList<>();

        if (searchFlightsRequest.getFrom().equals(searchFlightsRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        for (Flight listFlight : flights) {
            LocalDate listFlightDeparture = listFlight.getDepartureTime().toLocalDate();

            if (listFlight.getFrom().getAirport().equals(searchFlightsRequest.getFrom())
                    && listFlight.getTo().getAirport().equals(searchFlightsRequest.getTo())
                    && listFlightDeparture.equals(searchFlightDeparture)) {
                foundFlights.add(listFlight);
            }
        }

        return foundFlights;
    }

    private boolean strangeDates(Flight flight) {
        LocalDateTime departure = flight.getDepartureTime();
        LocalDateTime arrival = flight.getArrivalTime();

        return departure.isEqual(arrival) || arrival.isBefore(departure);
    }
}
