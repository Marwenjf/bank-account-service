package org.jrd.bankaccountservice.web;

import org.jrd.bankaccountservice.dto.BankAccountRequestDTO;
import org.jrd.bankaccountservice.dto.BankAccountResponseDTO;
import org.jrd.bankaccountservice.entities.BankAccount;
import org.jrd.bankaccountservice.mappers.AccountMapper;
import org.jrd.bankaccountservice.repositories.BankAccountRepository;
import org.jrd.bankaccountservice.repositories.CustomerRepository;
import org.jrd.bankaccountservice.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    private BankAccountRepository bankAccountRepository;
    private AccountService accountService;
    private AccountMapper accountMapper;
    private CustomerRepository customerRepository;

    public AccountRestController(BankAccountRepository bankAccountRepository, AccountService accountService, AccountMapper accountMapper, CustomerRepository customerRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.customerRepository = customerRepository;
    }
    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts(){
        return bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount bankAccount(@PathVariable String id)  {
        return bankAccountRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
    }

    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO bankAccount){
        return accountService.addAccount(bankAccount);
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccount update(String id,@RequestBody BankAccount bankAccount){
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
        if (bankAccount.getBalance()!=null)account.setBalance(bankAccount.getBalance());
        if (bankAccount.getCurrency()!=null)account.setCurrency(bankAccount.getCurrency());
        if (bankAccount.getType()!=null)account.setType(bankAccount.getType());
        if (bankAccount.getCreatedAt()!=null)account.setCreatedAt(bankAccount.getCreatedAt());
        return bankAccountRepository.save(account);
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void delete(@PathVariable String id)  {
         bankAccountRepository.deleteById(id);
    }
}
