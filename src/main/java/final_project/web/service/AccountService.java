package final_project.web.service;

import final_project.web.entity.Account;
import final_project.web.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(UUID accountId, Account accountDetails) {
        return accountRepository.findById(accountId).map(account -> {
            account.setEmail(accountDetails.getEmail());
            account.setUsername(accountDetails.getUsername());
            account.setPassword(accountDetails.getPassword());
            account.setShippingAddress(accountDetails.getShippingAddress());
            account.setBillingAddress(accountDetails.getBillingAddress());
            account.setPaymentMethod(accountDetails.getPaymentMethod());
            return accountRepository.save(account);
        }).orElse(null);
    }

    public Optional<Account> lookupAccount(UUID accountId) {
        return accountRepository.findById(accountId);
    }
}