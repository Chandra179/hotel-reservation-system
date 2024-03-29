package com.hospitality.hotelreservation.service;

import com.hospitality.hotelreservation.entity.Hotel;
import com.hospitality.hotelreservation.repository.HotelRepository;
import com.hospitality.hotelreservation.valueobjects.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SearchServiceTest {

  @Mock
  private HotelRepository hotelRepository;

  @InjectMocks
  private SearchService searchService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void searchHotels_callsRepositoryAndReturnsResults() {
    // Set up test data
    SearchCriteria criteria = new SearchCriteria("Paris", LocalDate.of(2024, 4, 15), LocalDate.of(2024, 4, 18), 10);
    List<Hotel> expectedHotels = List.of(new Hotel("Hotel A", "Paris"), new Hotel("Hotel B", "Paris"));

    // Mock repository behavior
    when(hotelRepository.findAvailableHotels(criteria.getLocation(), criteria.getCheckInDate(), criteria.getCheckOutDate()))
      .thenReturn(expectedHotels);

    // Call the method under test
    List<Hotel> foundHotels = searchService.searchHotels(criteria);

    // Verify interactions and results
    verify(hotelRepository).findAvailableHotels(criteria.getLocation(), criteria.getCheckInDate(), criteria.getCheckOutDate());
    assertEquals(expectedHotels, foundHotels);
  }
}
