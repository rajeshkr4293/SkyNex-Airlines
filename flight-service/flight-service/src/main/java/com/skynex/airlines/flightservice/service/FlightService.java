package com.skynex.airlines.flightservice.service;

import com.skynex.airlines.flightservice.model.Flight;
import com.skynex.airlines.flightservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(Long id){
        return flightRepository.findById(id);
    }

    public Flight addFlight(Flight flight){
        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id){
         flightRepository.deleteById(id);
    }

    public void updateAvailableSeats(Long flightId, int seatsBooked) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));

        if (flight.getAvailableSeats() < seatsBooked) {
            throw new IllegalArgumentException("Not enough seats available!");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - seatsBooked);
        flightRepository.save(flight);
    }


}
