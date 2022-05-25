package io.codelex.flightplanner.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Airport {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    @Id
    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public Airport() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;
        Airport airport1 = (Airport) o;
        return country.toLowerCase().replaceAll("\\s+","").equals(airport1.country.toLowerCase().replaceAll("\\s+",""))
                && city.toLowerCase().replaceAll("\\s+","").equals(airport1.city.toLowerCase().replaceAll("\\s+",""))
                && airport.toLowerCase().replaceAll("\\s+","").equals(airport1.airport.toLowerCase().replaceAll("\\s+",""));
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}