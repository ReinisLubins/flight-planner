package io.codelex.flightplanner.service;

import io.codelex.flightplanner.repository.FlightInMemoryRepository;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
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
    FlightInMemoryRepository flightRepository;

    @InjectMocks
    FlightServiceInMemory flightServiceInMemory;

    @Test
    public void testAddFlight() {
        Airport from = new Airport("Latvia", "Riga", "RIX");
        Airport to = new Airport("Estonia", "Tallinn", "EST");
        String departureTime = "2022-05-02 08:30";
        String arrivalTime = "2022-05-02 10:30";

        //Airport from, Airport to, String carrier, String departureTime, String arrivalTime
        AddFlightRequest addFlightRequest = new AddFlightRequest(from, to, "Good", departureTime, arrivalTime);
        Flight flight = flightServiceInMemory.addFlight(addFlightRequest);

        Mockito.doAnswer(invocation -> {
            AddFlightRequest request = invocation.getArgument(0);
            Assertions.assertEquals(addFlightRequest, request);
            return new Flight(request);
        }).when(flightRepository).addFlight(flight);

        Mockito.verify(flightRepository).addFlight(flight);
    }

}
