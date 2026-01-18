package com.misael.pix_sistem.domain.service;

import com.misael.pix_sistem.api.dto.request.TransactionRequestDTO;
import com.misael.pix_sistem.api.dto.response.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {

    TransactionResponseDTO transaction(TransactionRequestDTO transactionRequestDTO);
    TransactionResponseDTO consultTransfers(Long id);
    List<TransactionResponseDTO> getAccountTransactionHistory(Long accountId);

}
