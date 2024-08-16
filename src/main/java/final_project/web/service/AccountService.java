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

    public Account register(Account account) {
        // Check if email already exists
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        // Save the new account
        return accountRepository.save(account);
    }

    // Login method
    public Account login(String email, String password) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            return account.get();
        }
        throw new RuntimeException("Invalid email or password");
    }

    public Optional<Account> lookupAccount(UUID accountId) {
        return accountRepository.findById(accountId);
    }
}