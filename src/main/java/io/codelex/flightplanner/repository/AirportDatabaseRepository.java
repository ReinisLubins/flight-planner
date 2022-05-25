package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportDatabaseRepository extends JpaRepository<Airport, String> {

    @Query(value = "SELECT a FROM Airport a WHERE LOWER(a.city) LIKE CONCAT('%', :searchPhrase,'%') "
            + "OR LOWER(a.country) LIKE CONCAT('%', :searchPhrase,'%')"
            + "OR LOWER(a.airport) LIKE CONCAT('%', :searchPhrase,'%')")
    List<Airport> searchAirportBySearchPhrase(@Param("searchPhrase") String searchPhrase);
}
