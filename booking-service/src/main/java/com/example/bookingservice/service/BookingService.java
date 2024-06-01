package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookingDTO;
import com.example.bookingservice.dto.PaymentDTO;
import com.example.bookingservice.entity.BookingInfoEntity;

public interface BookingService{

    public BookingInfoEntity acceptRoomBooking(BookingDTO bookingDTO);

    public BookingInfoEntity makePayment(int bookingId, PaymentDTO paymentDTO);

}