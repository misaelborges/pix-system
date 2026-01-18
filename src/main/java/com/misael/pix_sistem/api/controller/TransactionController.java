package com.misael.pix_sistem.api.controller;

import com.misael.pix_sistem.api.dto.request.TransactionRequestDTO;
import com.misael.pix_sistem.api.dto.response.TransactionResponseDTO;
import com.misael.pix_sistem.domain.service.impl.TransactionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/pix")
    public ResponseEntity<TransactionResponseDTO> transaction(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO transactionResponseDTO = transactionService.transaction(transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> consultTransfers(@PathVariable Long id) {
        TransactionResponseDTO transactionResponseDTO = transactionService.consultTransfers(id);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTO);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getAccountTransactionHistory(@PathVariable Long accountId) {
        List<TransactionResponseDTO> transactionResponseDTOS = transactionService.getAccountTransactionHistory(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDTOS);
    }
}
