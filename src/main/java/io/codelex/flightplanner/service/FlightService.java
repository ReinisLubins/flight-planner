package io.codelex.flightplanner.service;


import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.response.PageResult;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightsRequest;

import java.util.List;

public interface FlightService {

    void clearFlights();

    Flight addFlight(AddFlightRequest addFlightRequest);

    void deleteFlight(long id);

    List<Airport> searchAirports(String search);

    Flight findFlightById(long id);

    PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest);
}
