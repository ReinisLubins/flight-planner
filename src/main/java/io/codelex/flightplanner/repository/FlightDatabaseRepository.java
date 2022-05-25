package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightDatabaseRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.from = :from AND f.to = :to AND f.carrier = :carrier AND f.departureTime = :departureTime AND f.arrivalTime = :arrivalTime")
    Optional<Flight> findFlight(@Param("from") Airport from, @Param("to")Airport to,
                                @Param("carrier")String carrier, @Param("departureTime")LocalDateTime departureTime,
                                @Param("arrivalTime")LocalDateTime arrivalTime);

    @Query("SELECT f FROM Flight f WHERE f.from.airport = :from AND f.to.airport = :to AND f.departureTime = :departureTime")
    List<Flight> findFlights(@Param("from") String from, @Param("to") String to,
                             @Param("departureTime") LocalDateTime departureTime);
}
