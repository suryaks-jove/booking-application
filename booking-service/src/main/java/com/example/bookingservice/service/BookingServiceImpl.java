package com.example.bookingservice.service;

import com.example.bookingservice.*;
import com.example.bookingservice.dao.BookingDAO;
import com.example.bookingservice.dto.BookingDTO;
import com.example.bookingservice.dto.PaymentDTO;
import com.example.bookingservice.entity.BookingInfoEntity;
import com.example.bookingservice.exception.InvalidBookingId;
import com.example.bookingservice.exception.InvalidPaymentMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDAO bookingDAO;

    @Autowired
    private RestTemplate restTemplate;

    final int statusCode = 400;
    final String INVALID_BOOKING_ID = "Invalid booking Id";
    final String INVALID_PAYMENT_MODE = "Invalid mode of payment";

    @Override
    public BookingInfoEntity acceptRoomBooking(BookingDTO bookingDTO) {

        BookingInfoEntity bookingInfoEntity = new BookingInfoEntity();

        bookingInfoEntity.setFromDate(bookingDTO.getFromDate());
        bookingInfoEntity.setToDate(bookingDTO.getToDate());
        bookingInfoEntity.setAadharNumber(bookingDTO.getAadharNumber());
        bookingInfoEntity.setNumOfRooms(bookingDTO.getNumOfRooms());
        bookingInfoEntity.setRoomNumbers(getRandomRoomNumbers(bookingDTO.getNumOfRooms()));


        int numOfDays = (int) ChronoUnit.DAYS.between(bookingDTO.getFromDate(), bookingDTO.getToDate());
        bookingInfoEntity.setRoomPrice(1000*bookingDTO.getNumOfRooms()*numOfDays);

        bookingInfoEntity.setBookedOn(LocalDate.now());

        bookingDAO.<BookingInfoEntity>save(bookingInfoEntity);
        return bookingInfoEntity;
    }

    @Override
    public BookingInfoEntity makePayment(int bookingId, PaymentDTO paymentDTO) {

        String paymentUrl = "http://localhost:8083/payment/transaction";
        Integer transactionId = restTemplate.postForObject(paymentUrl, paymentDTO, Integer.class);

        Optional<BookingInfoEntity> bookingInfoEntity = bookingDAO.findById(paymentDTO.getBookingId());

//        Validation of Booking ID
        if (bookingInfoEntity.isPresent()) {

            BookingInfoEntity bookingInfo = bookingInfoEntity.get();
            bookingInfo.setTransactionId(transactionId);
            bookingDAO.<BookingInfoEntity>save(bookingInfo);
            String message = "Booking confirmed for user with aadhaar number: "
                    + bookingInfo.getAadharNumber()
                    +    "    |    "
                    + "Here are the booking details:    " + bookingInfo.toString();
            System.out.println(message);
            return bookingInfo;
        }
        else if(!bookingInfoEntity.isPresent())
            throw new InvalidBookingId(INVALID_BOOKING_ID, statusCode);

            //check if correct payment mode is opted
        else if(!(paymentDTO.getPaymentMode().equalsIgnoreCase("card") || paymentDTO.getPaymentMode().equalsIgnoreCase("upi"))){
            throw new InvalidPaymentMode(INVALID_PAYMENT_MODE, statusCode);
        }
        return null;

    }

    //    Function for random room numbers
    public String getRandomRoomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;

        String roomNumbers = "";

        for (int i=0; i<count; i++){
            roomNumbers+=String.valueOf(rand.nextInt(upperBound));
            if(i<count-1)
                roomNumbers+=",";
        }

        return roomNumbers;
    }
}