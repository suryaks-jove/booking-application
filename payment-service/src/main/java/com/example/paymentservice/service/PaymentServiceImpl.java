package com.example.paymentservice.service;


import com.example.paymentservice.dao.PaymentDAO;
import com.example.paymentservice.dto.PaymentDTO;
import com.example.paymentservice.entity.TransactionDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentDAO paymentDAO;

    @Override
    public TransactionDetailsEntity acceptTransaction(PaymentDTO paymentDTO) {

        TransactionDetailsEntity newTransaction = new TransactionDetailsEntity();
        newTransaction.setPaymentMode(paymentDTO.getPaymentMode());
        newTransaction.setBookingId(paymentDTO.getBookingId());
        newTransaction.setCardNumber(paymentDTO.getCardNumber());
        newTransaction.setUpiId(paymentDTO.getUpiId());

        return paymentDAO.save(newTransaction);

    }

    @Override
    public TransactionDetailsEntity getTransaction(int transactionId) {
        Optional<TransactionDetailsEntity> transactionDetailsEntity = paymentDAO.findTransactionDetailsEntityByTransactionId(transactionId);
        return transactionDetailsEntity.get();
    }


}
