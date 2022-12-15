package org.jrd.bankaccountservice.services;

import org.jrd.bankaccountservice.dto.BankAccountRequestDTO;
import org.jrd.bankaccountservice.dto.BankAccountResponseDTO;

public interface AccountService {
    BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);

    BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO);
}
