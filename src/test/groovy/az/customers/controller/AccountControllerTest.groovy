package az.customers.controller

import az.customers.MockData.MockData
import az.customers.exception.AccountNotFoundException
import az.customers.exception.CustomerNotFoundException
import az.customers.exception.ResourceAlreadyExistsException
import az.customers.exception.handler.GlobalExceptionHandler
import az.customers.model.dto.AccountsDto
import az.customers.model.enums.ErrorCodes
import az.customers.model.response.CommonResponse
import az.customers.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

class AccountControllerTest extends Specification {

    private static final URL = "/api/v1/{customerId}/accounts"

    private MockMvc mockMvc
    private AccountController controller
    private AccountService service
    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        service = Mock()
        controller = new AccountController(service)
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addPlaceholderValue("/api/v1/{customerId}/accounts", URL)
                .build()
        objectMapper.registerModule(new JavaTimeModule())
    }

    def "create account - success"() {
        given:
        def customerId = 1L
        def url = "/api/v1/{customerId}/accounts"
        def request = MockData.accountRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def response = MockData.accountResponse()
        def responseJson = objectMapper.writeValueAsString(
                CommonResponse.success("Account successfully created", response))

        when:
        def result = mockMvc.perform(
                post(url, customerId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.createAccount(customerId, _ as AccountsDto.Request) >> response
        result.response.status == HttpStatus.CREATED.value()
        JSONAssert.assertEquals(responseJson, result.response.getContentAsString(), false)
    }

    def " create account - Customer Not found"() {
        given:
        def customerId = 99L
        def url = "/api/v1/{customerId}/accounts"
        def request = MockData.accountRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new CustomerNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "customer not found exception")

        when:
        def result = mockMvc.perform(
                post(url, customerId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.createAccount(customerId, _ as AccountsDto.Request) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def " create account - account number already exists"() {
        given:
        def customerId = 99L
        def url = "/api/v1/{customerId}/accounts"
        def request = MockData.accountRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new ResourceAlreadyExistsException(ErrorCodes.ACCOUNT_NUMBER_EXISTS, "account number already exists")

        when:
        def result = mockMvc.perform(
                post(url, customerId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.createAccount(customerId, _ as AccountsDto.Request) >> { throw exception }
        result.response.status == HttpStatus.BAD_REQUEST.value()
    }

    def "get by account - success"() {
        given:
        def customerId = 1L
        def accountId = 1L
        def url = "/api/v1/{customerId}/accounts/{accountId}"
        def response = MockData.accountResponse()
        def responseJson = objectMapper.writeValueAsString(
                CommonResponse.success("Account successfully", response))

        when:
        def result = mockMvc.perform(get(url, customerId, accountId)).andReturn()

        then:
        1 * service.getAccountById(customerId, accountId) >> response
        result.response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(responseJson, result.response.getContentAsString(), false)
    }

    def "get by account - customer not found"() {
        given:
        def customerId = 9L
        def accountId = 1L
        def url = "/api/v1/{customerId}/accounts/{accountId}"
        def exception = new CustomerNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "customer not found")

        when:
        def result = mockMvc.perform(get(url, customerId, accountId)).andReturn()

        then:
        1 * service.getAccountById(customerId, accountId) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def "get by account - account not found"() {
        given:
        def customerId = 1L
        def accountId = 99L
        def url = "/api/v1/{customerId}/accounts/{accountId}"
        def exception = new AccountNotFoundException(ErrorCodes.ACCOUNT_NOT_FOUND, "account not found exception")

        when:
        def result = mockMvc.perform(get(url, customerId, accountId)).andReturn()

        then:
        1 * service.getAccountById(customerId, accountId) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def "update account - success"() {
        given:
        def customerId = 1L
        def accountId = 1L
        def url = "/api/v1/{customerId}/accounts/{accountId}"
        def request = MockData.accountUpdateRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def response = MockData.accountUpdateResponse()
        def responseJson = objectMapper.writeValueAsString(
                CommonResponse.success("Account successfully updated", response))

        when:
        def result = mockMvc.perform(
                put(url, customerId, accountId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.updateAccount(accountId, customerId, _ as AccountsDto.Update) >> response
        result.response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(responseJson, result.response.getContentAsString(), false)
    }

    def "update account - account not found exception"() {
        given:
        def customerId = 1L
        def accountId = 1L
        def url = "/api/v1/{customerId}/accounts/{accountId}"
        def request = MockData.accountUpdateRequest()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new AccountNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "account not found exception")

        when:
        def result = mockMvc.perform(
                put(url, customerId, accountId)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.updateAccount(accountId, customerId, _ as AccountsDto.Update) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def "delete account - success"() {
        given:
        def customerId = 1L
        def accountId = 1L
        def url = "/api/v1/{customerId}/accounts/{accountId}"

        when:
        mockMvc.perform(delete(url, customerId, accountId))

        then:
        1 * service.deleteAccountById(customerId, accountId)
    }

    def "delete account - account not found exception"() {
        given:
        def customerId = 1L
        def accountId = 1L
        def url = "/api/v1/{customerId}/accounts/{accountId}"
        def exception = new AccountNotFoundException(ErrorCodes.ACCOUNT_NOT_FOUND, "account not found exception")

        when:
        mockMvc.perform(delete(url, customerId, accountId))

        then:
        1 * service.deleteAccountById(customerId, accountId) >> { throw exception }
    }
}
