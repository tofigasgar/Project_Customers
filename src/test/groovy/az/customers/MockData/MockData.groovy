package az.customers.MockData

import az.customers.model.dto.AccountsDto
import az.customers.model.dto.AccountsTransactionDto
import az.customers.model.dto.CustomerDto
import az.customers.model.entity.Accounts
import az.customers.model.entity.Customer
import az.customers.model.enums.AccountStatus
import az.customers.model.enums.Currency
import az.customers.model.enums.Gender

class MockData {

    static final CustomerDto.Request request() {

        return CustomerDto.Request
                .builder()
                .name("Tofig")
                .surname("Asgarov")
                .phone("0107271701")
                .email("tofig@mail.ru")
                .passportSerial("AZE57155599")
                .fin("TYGD334")
                .gender(Gender.MALE)
                .build()
    }

    static final CustomerDto.Response response() {
        return CustomerDto.Response
                .builder()
                .id(1L)
                .name("Tofig")
                .surname("Asgarov")
                .fin("TYGD334")
                .passportSerial("AZE57155599")
                .gender(Gender.MALE)
                .phone("0107271701")
                .email("tofig@mail.ru")
                .build()
    }

    static final CustomerDto.Update updateRequestData() {
        return CustomerDto.Update
                .builder()
                .name("Aslan")
                .surname("Asgarov")
                .passportSerial("AZE57105599")
                .email("aslan@gmail.com")
                .phone("0107271702")
                .build()
    }

    static final CustomerDto.Response updateResponse() {
        return CustomerDto.Response
                .builder()
                .id(1L)
                .name("Aslan")
                .surname("Asgarov")
                .fin("TYGD334")
                .passportSerial("AZE57105599")
                .gender(Gender.MALE)
                .phone("0107271702")
                .email("aslan@gmail.com")
                .build()
    }

    static final AccountsDto.Request accountRequest() {
        return AccountsDto.Request
                .builder()
                .accountNumber("AZ21BBBZ00111114107000345678")
                .currency(Currency.AZN)
                .build()
    }

    static final AccountsDto.Response accountResponse() {
        AccountsDto.Response
                .builder()
                .id(1L)
                .balance(new BigDecimal("0"))
                .accountNumber("AZ21BBBZ00111114107000345678")
                .currency(Currency.AZN)
                .customerFin("(93FDDFD")
                .build()
    }

    static final AccountsDto.Update accountUpdateRequest() {
        AccountsDto.Update
                .builder()
                .status(AccountStatus.BLOCKED)
                .build()
    }

    static final AccountsDto.Response accountUpdateResponse() {
        AccountsDto.Response
                .builder()
                .id(1L)
                .balance(new BigDecimal("0"))
                .accountNumber("AZ21BBBZ00111114107000345678")
                .currency(Currency.AZN)
                .customerFin("(93FDDFD")
                .status(AccountStatus.BLOCKED)
                .build()
    }

    static final AccountsTransactionDto.Request transactionRequest() {
        AccountsTransactionDto.Request
                .builder()
                .amount(new BigDecimal("1500"))
                .description("deposit transaction")
                .type("DEPOSIT")
                .build()
    }

    static final AccountsTransactionDto.Response transactionResponse() {
        AccountsTransactionDto.Response
                .builder()
                .id(1L)
                .amount(new BigDecimal("1500"))
                .beforeBalance(new BigDecimal("0"))
                .afterBalance(new BigDecimal("1500"))
                .description("deposit transaction")
                .type("DEPOSIT")
                .accountNumber("AZ21BBBZ00111114107000345678")
                .build()
    }

    static final Customer customerEntity() {
        Customer
                .builder()
                .id(1L)
                .name("Tofig")
                .surname("Asgarov")
                .fin("TYGD334")
                .passportSerial("AZE57155599")
                .gender(Gender.MALE)
                .phone("0107271701")
                .email("tofig@mail.ru")
                .build()
    }

    static final Customer customerEntityForUpdate() {
        Customer
                .builder()
                .id(1L)
                .name("Tofig")
                .surname("Asgarov")
                .fin("TYGD334")
                .passportSerial("AZE57105599")
                .gender(Gender.MALE)
                .phone("0107271701")
                .email("tofig@mail.ru")
                .build()
    }

    static final Accounts accountEntity() {
        Accounts
                .builder()
                .id(1L)
                .balance(new BigDecimal("0"))
                .accountNumber("AZ21BBBZ00111114107000345678")
                .currency(Currency.AZN)
                .build()
    }

    static final AccountsDto.Update accountRequestUpdate() {
        AccountsDto.Update
                .builder()
                .status(AccountStatus.ACTIVE)
                .build()
    }


}
