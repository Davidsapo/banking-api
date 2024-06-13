package com.fintech.bankingapi.model.dto;

import com.fintech.bankingapi.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionDTO {
    private UUID id;
    private TransactionType type;
    private AccountDTO account;
    private AccountDTO targetAccount;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
