package az.customers.controller

import az.customers.MockData.MockData
import az.customers.exception.AccountNotFoundException
import az.customers.exception.InactiveAccountException
import az.customers.exception.InsufficientFundsException
import az.customers.exception.handler.GlobalExceptionHandler
import az.customers.model.dto.AccountsTransactionDto
import az.customers.model.enums.ErrorCodes
import az.customers.model.response.CommonResponse
import az.customers.service.ProcedureService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class ProcedureControllerTest extends Specification {

    private static final URL = "/api/v1/{accountId}/procedures"
    private MockMvc mockMvc
    private ProcedureController controller
    private ProcedureService service
    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        service = Mock()
        controller = new ProcedureController(service)
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addPlaceholderValue("/api/v1/{accountId}/procedures", URL)
                .build()
        objectMapper.registerModule(new JavaTimeModule())
    }

    def "create transaction success"() {
        given:
        def accountId = 1L
        def url = "/api/v1/{accountId}/procedures"
        def request = MockData.transactionRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def response = MockData.transactionResponse()
        def responseJson = objectMapper.writeValueAsString(
                CommonResponse.success("Account transaction successfully created", response))

        when:
        def result = mockMvc
                .perform(post(url, accountId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.response(accountId, _ as AccountsTransactionDto.Request) >> response
        result.response.status == HttpStatus.CREATED.value()
        JSONAssert.assertEquals(responseJson, result.response.getContentAsString(), false)
    }

    def "create transaction INSUFFICIENT FUNDS exception"() {
        given:
        def accountId = 1L
        def url = "/api/v1/{accountId}/procedures"
        def request = MockData.transactionRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new InsufficientFundsException(ErrorCodes.INSUFFICIENT_FUNDS, "insufficient funds")

        when:
        def result = mockMvc
                .perform(post(url, accountId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.response(accountId, _ as AccountsTransactionDto.Request) >> { throw exception }
        result.response.status == HttpStatus.BAD_REQUEST.value()
    }

    def "create transaction INACTIVE Account exception"() {
        given:
        def accountId = 1L
        def url = "/api/v1/{accountId}/procedures"
        def request = MockData.transactionRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new InactiveAccountException(ErrorCodes.INACTIVE_ACCOUNT, "inactive account")

        when:
        def result = mockMvc
                .perform(post(url, accountId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.response(accountId, _ as AccountsTransactionDto.Request) >> { throw exception }
        result.response.status == HttpStatus.FORBIDDEN.value()
    }

    def "create transaction Blocked Account exception"() {
        given:
        def accountId = 1L
        def url = "/api/v1/{accountId}/procedures"
        def request = MockData.transactionRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new InactiveAccountException(ErrorCodes.BLOCKED_ACCOUNT, "blocked account")

        when:
        def result = mockMvc
                .perform(post(url, accountId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.response(accountId, _ as AccountsTransactionDto.Request) >> { throw exception }
        result.response.status == HttpStatus.FORBIDDEN.value()
    }

    def "create transaction AccountNotFound exception"() {
        given:
        def accountId = 1L
        def url = "/api/v1/{accountId}/procedures"
        def request = MockData.transactionRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new AccountNotFoundException(ErrorCodes.ACCOUNT_NOT_FOUND, "account not found")

        when:
        def result = mockMvc
                .perform(post(url, accountId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.response(accountId, _ as AccountsTransactionDto.Request) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def "get transaction by id - success"() {
        given:
        def accountId = 1L
        def transactionId = 1L
        def url = "/api/v1/{accountId}/procedures/{transactionId}"
        def response = MockData.transactionResponse()
        def responseJson = objectMapper.writeValueAsString(
                CommonResponse.success("Successfully", response))

        when:
        def result = mockMvc.perform(get(url, accountId, transactionId)).andReturn()

        then:
        1 * service.getTransactionById(accountId, transactionId) >> response
        result.response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(responseJson, result.response.getContentAsString(), false)
    }

    def "get transaction by id - account not found exception"() {
        given:
        def accountId = 1L
        def transactionId = 1L
        def url = "/api/v1/{accountId}/procedures/{transactionId}"
        def exception = new AccountNotFoundException(ErrorCodes.ACCOUNT_NOT_FOUND, "account not found")

        when:
        def result = mockMvc.perform(get(url, accountId, transactionId)).andReturn()

        then:
        1 * service.getTransactionById(accountId, transactionId) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def "get transaction by id - transaction not found exception"() {
        given:
        def accountId = 1L
        def transactionId = 1L
        def url = "/api/v1/{accountId}/procedures/{transactionId}"
        def exception = new AccountNotFoundException(ErrorCodes.TRANSACTION_NOT_FOUND, "transaction not found")

        when:
        def result = mockMvc.perform(get(url, accountId, transactionId)).andReturn()

        then:
        1 * service.getTransactionById(accountId, transactionId) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }
}
