package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentDTO;
import com.example.paymentservice.entity.TransactionDetailsEntity;

public interface PaymentService {

    public TransactionDetailsEntity acceptTransaction(PaymentDTO paymentDTO);

    public TransactionDetailsEntity getTransaction(int transactionId);

}
