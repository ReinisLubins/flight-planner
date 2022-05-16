package io.codelex.flightplanner.DatabaseServices;

import io.codelex.flightplanner.AirportAndFlight.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportDatabaseRepository extends JpaRepository<Airport, String> {

}
