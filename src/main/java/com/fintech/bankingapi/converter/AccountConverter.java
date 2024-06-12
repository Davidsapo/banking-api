package com.fintech.bankingapi.converter;

import com.fintech.bankingapi.model.dto.AccountDTO;
import com.fintech.bankingapi.model.entity.Account;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Account converter
 *
 * @author David Sapozhnik
 */
public class AccountConverter {

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
}
