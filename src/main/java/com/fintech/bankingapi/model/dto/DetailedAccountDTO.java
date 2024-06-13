package com.fintech.bankingapi.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedAccountDTO extends AccountDTO {

    private final List<TransactionDTO> transactions = new ArrayList<>();
}