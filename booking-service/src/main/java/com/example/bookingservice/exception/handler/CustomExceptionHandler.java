package com.example.bookingservice.exception.handler;


import com.example.bookingservice.dto.ExceptionResponse;
import com.example.bookingservice.exception.InvalidBookingId;
import com.example.bookingservice.exception.InvalidPaymentMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidPaymentMode.class)
    public final ResponseEntity<ExceptionResponse> handlePaymentException(InvalidPaymentMode e, WebRequest request){

        ExceptionResponse exceptionResponse =new ExceptionResponse();
        exceptionResponse.setMessage("Invalid Mode of Payment");
        exceptionResponse.setStatusCode(400);
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBookingId.class)
    public final ResponseEntity<ExceptionResponse> handleBookingException(InvalidBookingId e, WebRequest request){

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage("Invalid Booking ID");
        exceptionResponse.setStatusCode(400);

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
