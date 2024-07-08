package com.lakeside.lakesidehotel.service;

import com.lakeside.lakesidehotel.models.BookedRoom;

import java.util.List;

public interface IBookedRoomService {
    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    void cancelBooking(Long bookingId);
}
