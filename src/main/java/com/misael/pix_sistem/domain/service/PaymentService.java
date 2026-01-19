package com.misael.pix_sistem.domain.service;

import com.misael.pix_sistem.api.dto.request.PaymentRequestDTO;
import com.misael.pix_sistem.api.dto.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    PaymentResponseDTO transaction(PaymentRequestDTO paymentRequestDTO);
    PaymentResponseDTO consultTransfers(Long id);
    List<PaymentResponseDTO> getAccountTransactionHistory(Long accountId);

}
