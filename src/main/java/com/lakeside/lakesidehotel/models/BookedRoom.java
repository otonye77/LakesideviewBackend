package com.lakeside.lakesidehotel.models;

import java.time.LocalDate;

public class BookedRoom {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int NumOfChildren;
    private int NumOfAdults;
    private int totalNumOfGuest;
    private String bookingConfirmationCode;
    private Room room;

    public void calculateNumberOfGuest(){
        this.totalNumOfGuest = this.NumOfAdults + this.NumOfChildren;
    }

    public void setNumOfAdults(int numOfAdults) {
        NumOfAdults = numOfAdults;
        calculateNumberOfGuest();
    }

    public void setNumOfChildren(int numOfChildren) {
        NumOfChildren = numOfChildren;
        calculateNumberOfGuest();
    }
}
