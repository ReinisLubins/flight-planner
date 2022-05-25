package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.SearchFlightsRequest;

import java.time.LocalDateTime;

public abstract class AbstractFlightService {

    protected boolean isTheSameFlightFromTo(Flight flight) {
        return flight.getFrom().equals(flight.getTo());
    }

    protected boolean isTheSameSearchFlightFromTo(SearchFlightsRequest searchFlightsRequest) {
        return searchFlightsRequest.getFrom().equals(searchFlightsRequest.getTo());
    }

    protected boolean isInvalidDates(Flight flight) {
        LocalDateTime departure = flight.getDepartureTime();
        LocalDateTime arrival = flight.getArrivalTime();

        return departure.isEqual(arrival) || arrival.isBefore(departure);
    }
}
