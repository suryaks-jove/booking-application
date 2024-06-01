package com.example.bookingservice.dao;


import com.example.bookingservice.entity.BookingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface BookingDAO extends JpaRepository<BookingInfoEntity, Integer> {

}