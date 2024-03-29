package com.hospitality.hotelreservation.valueobjects;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Availability {

    private final Long roomId; // Room ID for which availability is checked
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final boolean available; // True if room is available for these dates

    public Availability(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, boolean available) {
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.available = available;
    }

    // Override equals() and hashCode() for meaningful comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        return roomId.equals(that.roomId) &&
                checkInDate.equals(that.checkInDate) &&
                checkOutDate.equals(that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, checkInDate, checkOutDate);
    }
}
