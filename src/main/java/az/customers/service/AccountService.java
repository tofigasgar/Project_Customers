package az.customers.service;

import az.customers.exception.AccountNotFoundException;
import az.customers.exception.ResourceAlreadyExistsException;
import az.customers.logger.DPLogger;
import az.customers.model.dto.AccountsDto;
import az.customers.model.entity.Accounts;
import az.customers.model.enums.AccountStatus;
import az.customers.model.enums.ErrorCodes;
import az.customers.repository.AccountRepository;
import az.customers.util.mapping.AccountsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final DPLogger logger = DPLogger.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final AccountsMapper accountsMapper;
    private final CustomerService customerService;

    public AccountsDto.Response createAccount(Long customerId, AccountsDto.Request request) {
        logger.info("Create account for customer: {}", customerId);
        var customer = customerService.findById(customerId);
        logger.info("Get Customer by id: {}", customerId);
        if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            logger.warn("Account number already exists: {}", request.getAccountNumber());
            throw new ResourceAlreadyExistsException(ErrorCodes.ACCOUNT_NUMBER_EXISTS, "account number already exists exception");
        }
        var account = accountsMapper.toEntity(request);
        account.setBalance(BigDecimal.ZERO);
        account.setCustomer(customer);
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        logger.info("Account successfully created for customer: {}", customerId);
        return accountsMapper.toDto(account);
    }

    public AccountsDto.Response getAccountById(Long customerId, Long accountId) {
        logger.info("Get account by id: {}", accountId);
        var account = getAccountByIdAndCustomerId(accountId, customerId);
        logger.info("Account successfully found by id: {}", accountId);
        return accountsMapper.toDto(account);
    }

    public AccountsDto.Response updateAccount(Long accountId, Long customerId, AccountsDto.Update request) {
        logger.info("Account updating beginning....");
        var accounts = getAccountByIdAndCustomerId(customerId, accountId);
        logger.info("Account updating with id: {}", accountId);
        accountsMapper.updateAccountFromRequest(request, accounts);
        accountRepository.save(accounts);
        logger.info("Account successfully updated account with id={}, accountNumber = {}",
                accountId, accounts.getAccountNumber());
        return accountsMapper.toDto(accounts);
    }

    public void deleteAccountById(Long customerId, Long accountId) {
        logger.info("Account deleting beginning....");
        var account = getAccountByIdAndCustomerId(accountId, customerId);
        logger.info("Account deleting with id: {}", accountId);
        account.setActive(false);
        accountRepository.save(account);
        logger.info("Account successfully deleted account with id={}, accountNumber = {}",
                accountId, account.getAccountNumber());
        logger.info("Account deleting finished....");
    }

    private Accounts getAccountByIdAndCustomerId(Long accountId, Long customerId) {
        return accountRepository.findByIdAndCustomerId(accountId, customerId).orElseThrow(() -> {
            logger.warn("Account not found with id: {}", accountId);
            return new AccountNotFoundException(ErrorCodes.ACCOUNT_NOT_FOUND, "Account not found exception");
        });
    }
}
