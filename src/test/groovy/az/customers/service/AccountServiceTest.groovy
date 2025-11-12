package az.customers.service

import az.customers.MockData.MockData
import az.customers.exception.AccountNotFoundException
import az.customers.exception.CustomerNotFoundException
import az.customers.model.enums.ErrorCodes
import az.customers.repository.AccountRepository
import az.customers.util.mapping.AccountsMapper
import spock.lang.Specification

class AccountServiceTest extends Specification {

    private AccountService service
    private CustomerService customerService
    private AccountRepository repository
    private AccountsMapper mapper

    def setup() {
        service = Mock()
        repository = Mock()
        mapper = Mock()
        customerService = Mock()
        service = new AccountService(repository, mapper, customerService)
    }

    def "create account - success"() {
        given:
        def id = 1L
        def request = MockData.accountRequest()
        def response = MockData.accountResponse()
        def account = MockData.accountEntity()
        def customer = MockData.customerEntity()

        when:
        def result = service.createAccount(id, request)

        then:
        1 * customerService.findById(id) >> customer
        1 * mapper.toEntity(request) >> account
        1 * repository.save(account) >> account
        1 * mapper.toDto(account) >> response
        result == response
    }

    def "create account - customer not found"() {
        given:
        def customerId = 99L
        def request = MockData.accountRequest()

        when:
        service.createAccount(customerId, request)

        then:
        1 * customerService.findById(customerId) >> {
            throw new CustomerNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "customer not found exception")
        }
        0 * mapper.toEntity(_)
        def ex = thrown(CustomerNotFoundException)
        ex.errorCode == ErrorCodes.CUSTOMER_NOT_FOUND
        ex.message == "customer not found exception"
    }

    def "get account by id - success"() {
        given:
        def customerId = 1L
        def accountId = 1L
        def response = MockData.accountResponse()
        def account = MockData.accountEntity()

        when:
        def result = service.getAccountById(customerId, accountId)

        then:
        1 * repository.findByIdAndCustomerId(customerId, accountId) >> Optional.of(account)
        1 * mapper.toDto(account) >> response
        result == response
    }

    def "get account by id - account not found"() {
        given:
        def customerId = 2L
        def accountId = 99L

        when:
        service.getAccountById(customerId, accountId)

        then:
        1 * repository.findByIdAndCustomerId(customerId, accountId) >> Optional.empty()
        0 * mapper.toDto(_)
        def ex = thrown(AccountNotFoundException)
        ex.errorCode == ErrorCodes.ACCOUNT_NOT_FOUND
        ex.message == "Account not found exception"
    }

    def "update account - success"() {
        given:
        def accountId = 1L
        def customerId = 1L
        def request = MockData.accountRequestUpdate()
        def response = MockData.accountResponse()
        def account = MockData.accountEntity()

        when:
        def result = service.updateAccount(accountId, customerId, request)

        then:
        1 * repository.findByIdAndCustomerId(customerId, accountId) >> Optional.of(account)
        1 * mapper.updateAccountFromRequest(request,account)
        1 * repository.save(account) >> account
        1 * mapper.toDto(account) >> response
        result == response
    }

    def "update account - account not found exception"() {
        given:
        def accountId = 1L
        def customerId = 1L
        def request = MockData.accountRequestUpdate()

        when:
        service.updateAccount(accountId, customerId, request)

        then:
        1 * repository.findByIdAndCustomerId(customerId, accountId) >> Optional.empty()
        def ex = thrown(AccountNotFoundException)
        ex.errorCode == ErrorCodes.ACCOUNT_NOT_FOUND
        ex.message == "Account not found exception"
    }

}

