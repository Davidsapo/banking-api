package com.fintech.bankingapi.converter;

import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.dto.DetailedAccountDTO;
import com.fintech.bankingapi.model.entity.Account;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Account converter
 *
 * @author David Sapozhnik
 */
public final class AccountConverter {

    private AccountConverter() {
    }

    public static AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setCreatedAt(account.getCreatedAt());
        accountDTO.setUpdatedAt(account.getUpdatedAt());
        return accountDTO;
    }

    public static List<AccountDTO> convertToDTOs(Collection<Account> accounts) {
        return accounts.stream()
                .map(AccountConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public static DetailedAccountDTO convertToDetailedDTO(Account account) {
        DetailedAccountDTO detailedAccountDTO = new DetailedAccountDTO();
        detailedAccountDTO.setId(account.getId());
        detailedAccountDTO.setAccountNumber(account.getAccountNumber());
        detailedAccountDTO.setBalance(account.getBalance());
        detailedAccountDTO.setCreatedAt(account.getCreatedAt());
        detailedAccountDTO.setUpdatedAt(account.getUpdatedAt());
        detailedAccountDTO.getTransactions().addAll(TransactionConverter.convertToDTOs(account.getTransactions()));
        return detailedAccountDTO;
    }
}
