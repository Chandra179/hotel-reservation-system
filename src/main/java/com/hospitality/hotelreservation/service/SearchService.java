package com.hospitality.hotelreservation.service;

import com.hospitality.hotelreservation.entity.Hotel;
import com.hospitality.hotelreservation.repository.HotelRepository;
import com.hospitality.hotelreservation.valueobjects.SearchCriteria;

import java.util.List;

public class SearchService {

    private HotelRepository hotelRepository;  // Replace with your actual Hotel data access

    public SearchService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> searchHotels(SearchCriteria criteria) {
        // Implement logic to retrieve hotels based on criteria (location, date, etc.)
        // Leverage hotelRepository to access hotel data
        List<Hotel> availableHotels = hotelRepository.findAvailableHotels(criteria.getLocation(), criteria.getCheckInDate(), criteria.getCheckOutDate());
        return availableHotels;
    }
}