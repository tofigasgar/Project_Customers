package az.customers.service;

import az.customers.exception.AccountNotFoundException;
import az.customers.exception.InactiveAccountException;
import az.customers.exception.InsufficientFundsException;
import az.customers.exception.TransactionNotFoundException;
import az.customers.logger.DPLogger;
import az.customers.model.dto.AccountsTransactionDto;
import az.customers.model.enums.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ProcedureService {

    private static final DPLogger logger = DPLogger.getLogger(ProcedureService.class);

    private final JdbcTemplate jdbcTemplate;

    public AccountsTransactionDto.Response response(Long accountId, AccountsTransactionDto.Request request) {
        logger.info("Create account transaction beginning for account: {}", accountId);
        try {
            String sql = "CALL create_account_transaction(?, ?, ?, ?)";

            var data = jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(AccountsTransactionDto.Response.class),
                    accountId,
                    request.getAmount(),
                    request.getDescription(),
                    request.getType()
            );
            logger.info("Create account transaction successfully for account: {}", accountId);
            return data;
        } catch (DataAccessException ex) {
            throw mapDatabaseException(ex);
        }
    }


    public AccountsTransactionDto.Response getTransactionById(Long accountId, Long transactionId) {
        try {
            logger.info("Get account transaction by id: {}", transactionId);
            String sql = "CALL get_account_transaction_by_id(?, ?)";

            var data = jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(AccountsTransactionDto.Response.class),
                    accountId,
                    transactionId
            );
            logger.info("Get account transaction successfully by id: {}", transactionId);
            return data;
        } catch (DataAccessException ex) {
            throw mapDatabaseException(ex);
        }
    }

    private RuntimeException mapDatabaseException(DataAccessException ex) {
        Throwable root = ex.getRootCause();
        if (root instanceof SQLException sqlEx) {
            String msg = sqlEx.getMessage();

            if (msg.contains("Insufficient funds")) {
                return new InsufficientFundsException(ErrorCodes.INSUFFICIENT_FUNDS, "insufficient funds");
            } else if (msg.contains("Account is INACTIVE")) {
                return new InactiveAccountException(ErrorCodes.INACTIVE_ACCOUNT, "inactive account");
            } else if (msg.contains("Account is BLOCKED")) {
                return new InactiveAccountException(ErrorCodes.BLOCKED_ACCOUNT, "blocked account");
            } else if (msg.contains("Account not found")) {
                return new AccountNotFoundException(ErrorCodes.ACCOUNT_NOT_FOUND, "account not found");
            } else if (msg.contains("Active transaction not found for this account")) {
                return new TransactionNotFoundException(ErrorCodes.TRANSACTION_NOT_FOUND, "transaction not found");
            }
        }
        return new RuntimeException(ex.getMessage());
    }
}
