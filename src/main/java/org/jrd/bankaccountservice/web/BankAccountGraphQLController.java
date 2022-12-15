package org.jrd.bankaccountservice.web;

import org.jrd.bankaccountservice.dto.BankAccountRequestDTO;
import org.jrd.bankaccountservice.dto.BankAccountResponseDTO;
import org.jrd.bankaccountservice.entities.BankAccount;
import org.jrd.bankaccountservice.entities.Customer;
import org.jrd.bankaccountservice.repositories.BankAccountRepository;
import org.jrd.bankaccountservice.repositories.CustomerRepository;
import org.jrd.bankaccountservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;

    @QueryMapping
    public List<BankAccount> accountList(){
        return bankAccountRepository.findAll();
    }

    @QueryMapping
    public List<Customer> customers(){
        return customerRepository.findAll();
    }
    @QueryMapping
    public BankAccount bankAccountById(@Argument String id){
        return bankAccountRepository.findById(id)
                .orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
    }
    @MutationMapping
    public BankAccountResponseDTO addAccount(@Argument BankAccountRequestDTO bankAccount){
        return accountService.addAccount(bankAccount);
    }

    @MutationMapping
    public BankAccountResponseDTO updateAccount(@Argument String id,@Argument BankAccountRequestDTO bankAccount){
        return accountService.updateAccount(id,bankAccount);
    }

    @MutationMapping
    public void deleteAccount(@Argument String id){
        bankAccountRepository.deleteById(id);
    }

}
