package com.misael.pix_sistem.api.controller;

import com.misael.pix_sistem.api.docs.PaymentControllerOpenApi;
import com.misael.pix_sistem.api.dto.request.PaymentRequestDTO;
import com.misael.pix_sistem.api.dto.response.PaymentResponseDTO;
import com.misael.pix_sistem.domain.service.impl.PaymentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class PaymentController implements PaymentControllerOpenApi {

    private final PaymentServiceImpl transactionService;

    public PaymentController(PaymentServiceImpl transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/pix")
    public ResponseEntity<PaymentResponseDTO> transaction(@RequestBody @Valid PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO paymentResponseDTO = transactionService.transaction(paymentRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> consultTransfers(@PathVariable Long id) {
        PaymentResponseDTO paymentResponseDTO = transactionService.consultTransfers(id);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDTO);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<PaymentResponseDTO>> getAccountTransactionHistory(@PathVariable Long accountId) {
        List<PaymentResponseDTO> paymentResponseDTOS = transactionService.getAccountTransactionHistory(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResponseDTOS);
    }
}
