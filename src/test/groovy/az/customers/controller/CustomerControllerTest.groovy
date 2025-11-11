package az.customers.controller

import az.customers.MockData.MockData
import az.customers.exception.CustomerNotFoundException
import az.customers.exception.ResourceAlreadyExistsException
import az.customers.exception.handler.GlobalExceptionHandler
import az.customers.model.dto.CustomerDto
import az.customers.model.enums.ErrorCodes
import az.customers.model.response.CommonResponse
import az.customers.service.CustomerService
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

class CustomerControllerTest extends Specification {

    private static final URL = "/api/v1/customers"

    private MockMvc mockMvc
    private CustomerService service
    private CustomerController controller
    private ObjectMapper objectMapper = new ObjectMapper()

    def setup() {
        service = Mock()
        controller = new CustomerController(service)
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addPlaceholderValue("/api/v1/customers", URL)
                .build()
        objectMapper.registerModule(new JavaTimeModule())
    }

    def "get customer by ID - success"() {
        given:
        def id = 1L
        def url = "/api/v1/customers/{id}"
        def data = MockData.response()
        def expectJson = objectMapper.writeValueAsString(CommonResponse.success(data))

        when:
        def result = mockMvc.perform(get(url, id)).andReturn()

        then:
        1 * service.getCustomerById(id) >> data
        result.response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectJson, result.response.getContentAsString(), false)
    }

    def "get customer by ID - CustomerNotFoundException"() {
        given:
        def id = 99L
        def url = "/api/v1/customers/{id}"
        def exception = new CustomerNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "customer not found exception")

        when:
        def result = mockMvc.perform(get(url, id)).andReturn()

        then:
        1 * service.getCustomerById(id) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def "create customer - success"() {
        given:
        def url = "/api/v1/customers"
        def request = MockData.request()
        def requestJson = objectMapper.writeValueAsString(request)
        def expectData = MockData.response()
        def expectJson = objectMapper.writeValueAsString(CommonResponse.success(expectData))

        when:
        def result = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
        then:

        1 * service.createCustomer(_ as CustomerDto.Request) >> expectData
        result.response.status == HttpStatus.CREATED.value()
        JSONAssert.assertEquals(expectJson, result.response.getContentAsString(), false)
    }

    def "create customer - customer already existing"() {
        given:
        def url = "/api/v1/customers"
        def request = MockData.request()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new ResourceAlreadyExistsException(ErrorCodes.CUSTOMER_ALREADY_EXISTS, "customer already exception")

        when:
        def result = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.createCustomer(_ as CustomerDto.Request) >> { throw exception }
        result.response.status == HttpStatus.BAD_REQUEST.value()
    }

    def "create customer - customer fin already existing"() {
        given:
        def url = "/api/v1/customers"
        def request = MockData.request()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new ResourceAlreadyExistsException(ErrorCodes.FIN_ALREADY_EXISTS, "customer already exception")

        when:
        def result = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.createCustomer(_ as CustomerDto.Request) >> { throw exception }
        result.response.status == HttpStatus.BAD_REQUEST.value()
    }

    def "create customer - customer passportSerial already existing"() {
        given:
        def url = "/api/v1/customers"
        def request = MockData.request()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new ResourceAlreadyExistsException(ErrorCodes.PASSPORT_SERIAL_EXISTS, "passportSerial already exception")

        when:
        def result = mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.createCustomer(_ as CustomerDto.Request) >> { throw exception }
        result.response.status == HttpStatus.BAD_REQUEST.value()
    }

    def "customer update data - success"() {
        given:
        def id = 1L
        def url = "/api/v1/customers/{id}"
        def request = MockData.updateRequestData()
        def requestJson = objectMapper.writeValueAsString(request)
        def expectData = MockData.updateResponse()
        def expectJson = objectMapper.writeValueAsString(CommonResponse.success(expectData))

        when:
        def result = mockMvc
                .perform(put(url, id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.updateCustomer(id, _ as CustomerDto.Update) >> expectData
        result.response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectJson, result.response.getContentAsString(), false)
    }

    def "customer update data - customerNot found exception"() {
        given:
        def id = 1L
        def url = "/api/v1/customers/{id}"
        def request = MockData.updateRequestData()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new CustomerNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "customer not found")

        when:
        def result = mockMvc
                .perform(put(url, id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.updateCustomer(id, _ as CustomerDto.Update) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def "customer update data - passport serial already exception exception"() {
        given:
        def id = 1L
        def url = "/api/v1/customers/{id}"
        def request = MockData.updateRequestData()
        def requestJson = objectMapper.writeValueAsString(request)
        def exception = new CustomerNotFoundException(ErrorCodes.PASSPORT_SERIAL_EXISTS, "passport serial already existis")

        when:
        def result = mockMvc
                .perform(put(url, id)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        1 * service.updateCustomer(id, _ as CustomerDto.Update) >> { throw exception }
        result.response.status == HttpStatus.NOT_FOUND.value()
    }

    def "customer delete - success"() {
        given:
        def id = 1L
        def url = "/api/v1/customers/{id}"

        when:
        mockMvc.perform(delete(url, id))

        then:
        1 * service.deleteCustomer(id)
    }

    def "customer delete - customer not found exception"() {
        given:
        def id = 1L
        def url = "/api/v1/customers/{id}"
        def exception = new CustomerNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "customer not found")

        when:
        mockMvc.perform(delete(url, id))

        then:
        1 * service.deleteCustomer(id) >> { throw exception }
    }

}
