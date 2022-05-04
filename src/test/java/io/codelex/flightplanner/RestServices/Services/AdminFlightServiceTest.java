package io.codelex.flightplanner.RestServices.Services;

import io.codelex.flightplanner.AirportAndFlight.AddFlightRequest;
import io.codelex.flightplanner.AirportAndFlight.Airport;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import io.codelex.flightplanner.RestServices.FlightRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminFlightServiceTest {

    @Mock
    FlightRepository flightRepository;

    @InjectMocks
    AdminFlightService adminFlightService;

    @Test
    public void testAddFlight() {
        Airport from = new Airport("Latvia", "Riga", "RIX");
        Airport to = new Airport("Estonia", "Tallinn", "EST");
        String departureTime = "2022-05-02 08:30";
        String arrivalTime = "2022-05-02 10:30";

        //Airport from, Airport to, String carrier, String departureTime, String arrivalTime
        AddFlightRequest addFlightRequest = new AddFlightRequest(from, to, "Good", departureTime, arrivalTime);

        Mockito.doAnswer(invocation -> {
            AddFlightRequest request = invocation.getArgument(0);
            Assertions.assertEquals(addFlightRequest, request);
            return new Flight(12, request);
        }).when(flightRepository).addFlight(addFlightRequest);

        Flight flight = adminFlightService.addFlight(addFlightRequest);

        Mockito.verify(flightRepository).addFlight(addFlightRequest);
    }

}
