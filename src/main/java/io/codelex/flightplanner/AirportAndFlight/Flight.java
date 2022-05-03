package io.codelex.flightplanner.AirportAndFlight;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {
    private long id;
    private Airport from;
    private Airport to;
    private String carrier;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight(long id, AddFlightRequest addFlightRequest) {
        this.id = id;
        this.from = addFlightRequest.getFrom();
        this.to = addFlightRequest.getTo();
        this.carrier = addFlightRequest.getCarrier();
        this.departureTime = LocalDateTime.parse(addFlightRequest.getDepartureTime(), formatter);
        this.arrivalTime = LocalDateTime.parse(addFlightRequest.getArrivalTime(), formatter);
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

    public static boolean searchedFlightsAreEqual(SearchFlightsRequest searchFlightsRequest, Flight flight) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchFlightDeparture = LocalDate.parse(searchFlightsRequest.getDepartureDate(), formatter);
        LocalDate listFlightDeparture = flight.getDepartureTime().toLocalDate();

        return flight.getFrom().getAirport().equals(searchFlightsRequest.getFrom())
                && flight.getTo().getAirport().equals(searchFlightsRequest.getTo())
                && listFlightDeparture.equals(searchFlightDeparture);
    }
}
