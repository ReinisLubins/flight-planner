package io.codelex.flightplanner.DatabaseServices;

import io.codelex.flightplanner.AirportAndFlight.Airport;
import org.springframework.stereotype.Service;

@Service
public class AirportDatabaseService {
    AirportDatabaseRepository airportDatabaseRepository;

    public AirportDatabaseService(AirportDatabaseRepository airportDatabaseRepository) {
        this.airportDatabaseRepository = airportDatabaseRepository;
    }

    public void addAirport(Airport airport) {
        airportDatabaseRepository.save(airport);
    }
}
