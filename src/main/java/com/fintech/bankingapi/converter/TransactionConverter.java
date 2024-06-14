package com.fintech.bankingapi.converter;

import com.fintech.bankingapi.model.dto.TransactionDTO;
import com.fintech.bankingapi.model.entity.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public final class TransactionConverter {

    private TransactionConverter() {
    }

    public static TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setTimestamp(transaction.getTimestamp());
        transactionDTO.setAccount(AccountConverter.convertToDTO(transaction.getAccount()));
        transactionDTO.setTargetAccount(nonNull(transaction.getTargetAccount()) ?
                AccountConverter.convertToDTO(transaction.getTargetAccount()) :
                null);
        return transactionDTO;
    }

    public static List<TransactionDTO> convertToDTOs(Collection<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionConverter::convertToDTO)
                .collect(Collectors.toList());
    }
}
