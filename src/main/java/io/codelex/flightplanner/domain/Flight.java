package io.codelex.flightplanner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.request.AddFlightRequest;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "flight_id")
    private long id;
    @JoinColumn(name = "from_id")
    @ManyToOne
    private Airport from;
    @JoinColumn(name = "to_id")
    @ManyToOne
    private Airport to;
    private String carrier;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;
    @Transient
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight(AddFlightRequest addFlightRequest) {
        this.from = addFlightRequest.getFrom();
        this.to = addFlightRequest.getTo();
        this.carrier = addFlightRequest.getCarrier();
        this.departureTime = LocalDateTime.parse(addFlightRequest.getDepartureTime(), formatter);
        this.arrivalTime = LocalDateTime.parse(addFlightRequest.getArrivalTime(), formatter);
    }

    public Flight() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public boolean searchedFlightsAreEqual(SearchFlightsRequest searchFlightsRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchFlightDeparture = searchFlightsRequest.getDepartureDate();
        LocalDate listFlightDeparture = getDepartureTime().toLocalDate();

        return getFrom().getAirport().equals(searchFlightsRequest.getFrom())
                && getTo().getAirport().equals(searchFlightsRequest.getTo())
                && listFlightDeparture.equals(searchFlightDeparture);
    }
}
