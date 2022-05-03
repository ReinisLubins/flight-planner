package io.codelex.flightplanner;

import io.codelex.flightplanner.AirportAndFlight.AddFlightRequest;
import io.codelex.flightplanner.AirportAndFlight.Airport;
import io.codelex.flightplanner.AirportAndFlight.Flight;
import io.codelex.flightplanner.RestServices.Controllers.AdminFlightController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightPlannerApplicationTests {

    @Autowired
    AdminFlightController adminFlightController;

    @Test
    void  addFlightsTest() {
        Airport from = new Airport("Latvia", "Riga", "RIX");
        Airport to = new Airport("Estonia", "Tallinn", "EST");
        String departureTime = "2022-05-02 08:30";
        String arrivalTime = "2022-05-02 10:30";

        AddFlightRequest addFlightRequest = new AddFlightRequest(from, to, "Good", departureTime, arrivalTime);

        Flight flight = adminFlightController.addFlight(addFlightRequest);

        //assertNotNull(flight.getId());
    }

}
