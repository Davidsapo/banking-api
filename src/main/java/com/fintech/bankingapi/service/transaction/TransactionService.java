package com.fintech.bankingapi.service.transaction;

import com.fintech.bankingapi.enums.TransactionType;
import com.fintech.bankingapi.model.dto.TransactionDTO;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionType type, UUID accountId, @Nullable UUID targetAccountId, BigDecimal amount);
}
