package com.lakeside.lakesidehotel.controller;

import com.lakeside.lakesidehotel.exception.InvalidBookingRequestException;
import com.lakeside.lakesidehotel.exception.ResourceNotFoundException;
import com.lakeside.lakesidehotel.models.BookedRoom;
import com.lakeside.lakesidehotel.models.Room;
import com.lakeside.lakesidehotel.response.BookingResponse;
import com.lakeside.lakesidehotel.response.RoomResponse;
import com.lakeside.lakesidehotel.service.IBookedRoomService;
import com.lakeside.lakesidehotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookedRoomController {
    private final IBookedRoomService bookingService;
    private final IRoomService roomService;


    @GetMapping("all-bookings")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<BookingResponse>> getAllBooking(){
        List<BookedRoom> bookings =  bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom booking: bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @GetMapping("/confirmation/{confirmationCode}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable  String confirmationCode) {
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/room/{roomId}/booking")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoom bookingRequest){
        try {
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok("Room Booked Successfully! Your booking confirmation code:" + confirmationCode);
        } catch (InvalidBookingRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    @CrossOrigin(origins = "http://localhost:5173")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }


    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom  = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId(), theRoom.getRoomType(), theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestEmail(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getTotalNumOfGuest(),
                booking.getBookingConfirmationCode(),
                room
        );
    }
}
