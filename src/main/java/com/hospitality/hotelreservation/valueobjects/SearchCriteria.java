package com.hospitality.hotelreservation.valueobjects;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class SearchCriteria {

    private final String location;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final int numGuests;

    public SearchCriteria(String location, LocalDate checkInDate, LocalDate checkOutDate, int numGuests) {
        this.location = location;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numGuests = numGuests;
    }

    // Override equals() and hashCode() for meaningful comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCriteria that = (SearchCriteria) o;
        return location.equals(that.location) &&
                checkInDate.equals(that.checkInDate) &&
                checkOutDate.equals(that.checkOutDate) &&
                numGuests == that.numGuests;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, checkInDate, checkOutDate, numGuests);
    }
}

