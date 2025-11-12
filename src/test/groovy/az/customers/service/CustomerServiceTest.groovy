package az.customers.service

import az.customers.MockData.MockData
import az.customers.exception.CustomerNotFoundException
import az.customers.exception.ResourceAlreadyExistsException
import az.customers.model.enums.ErrorCodes
import az.customers.repository.CustomerRepository
import az.customers.util.mapping.CustomerMapper
import spock.lang.Specification

class CustomerServiceTest extends Specification {

    private CustomerService service
    private CustomerRepository repository
    private CustomerMapper customerMapper

    def setup() {
        service = Mock()
        repository = Mock()
        customerMapper = Mock()
        service = new CustomerService(repository, customerMapper)
    }

    def "get customer by id"() {
        given:
        def id = 1L
        def response = MockData.response()
        def customer = Optional.of(MockData.customerEntity())

        when:
        def result = service.getCustomerById(id)

        then:
        1 * repository.findByIdAndActiveTrue(id) >> customer
        1 * customerMapper.toResponse(customer.get()) >> response
        result == response
    }

    def "get customer by id - customer not found exception"() {
        given:
        def id = 1L

        when:
        service.getCustomerById(id)

        then:
        1 * repository.findByIdAndActiveTrue(id) >> Optional.empty()
        0 * customerMapper.toResponse(_)
        def ex = thrown(CustomerNotFoundException)
        ex.message == "customer not found exception"
        ex.errorCode == ErrorCodes.CUSTOMER_NOT_FOUND
    }

    def "create customer - success"() {
        given:
        def request = MockData.request()
        def response = MockData.response()
        def customer = MockData.customerEntity()

        when:
        def result = service.createCustomer(request)

        then:
        1 * customerMapper.toEntity(request) >> customer
        1 * repository.save(customer) >> customer
        1 * customerMapper.toResponse(customer) >> response
        result == response
    }

    def "create customer - customer fin already exists exception"() {
        given:
        def request = MockData.request()

        when:
        service.createCustomer(request)

        then:
        1 * repository.existsByFin(request.getFin()) >> true
        def ex = thrown(ResourceAlreadyExistsException)
        ex.errorCode == ErrorCodes.FIN_ALREADY_EXISTS
        ex.message == "fin already exists exception"
    }

    def "create customer - passportSerial already exists exception"() {
        given:
        def request = MockData.request()

        when:
        service.createCustomer(request)

        then:
        1 * repository.existsByPassportSerial(request.getPassportSerial()) >> true
        def ex = thrown(ResourceAlreadyExistsException)
        ex.errorCode == ErrorCodes.PASSPORT_SERIAL_EXISTS
        ex.message == "passportSerial already exists exception"
    }

    def "update customer - success"() {
        given:
        def id = 1L
        def request = MockData.updateRequestData()
        def response = MockData.response()
        def customer = MockData.customerEntity()

        when:
        def result = service.updateCustomer(id, request)

        then:
        1 * repository.findByIdAndActiveTrue(id) >> Optional.of(customer)
        1 * customerMapper.updateEntityFromRequest(request, customer)
        1 * repository.save(customer) >> customer
        1 * customerMapper.toResponse(customer) >> response
        result == response
    }

    def "update customer - passportSerial already exists exception"() {
        given:
        def id = 1L
        def request = MockData.updateRequestData()
        def customer = MockData.customerEntityForUpdate()

        when:
        service.updateCustomer(id, request)

        then:
        1 * repository.findByIdAndActiveTrue(id) >> Optional.of(customer)
        def ex = thrown(ResourceAlreadyExistsException)
        ex.errorCode == ErrorCodes.PASSPORT_SERIAL_EXISTS
        ex.message == "passportSerial already exists exception"
    }

    def "update customer - customer not found exception"() {
        given:
        def id = 1L
        def request = MockData.updateRequestData()

        when:
        service.updateCustomer(id, request)

        then:
        1 * repository.findByIdAndActiveTrue(id) >> Optional.empty()
        def ex = thrown(CustomerNotFoundException)
        ex.errorCode == ErrorCodes.CUSTOMER_NOT_FOUND
        ex.message == "customer not found exception"
    }

    def "delete customer - success"() {
        given:
        def id = 1L
        def customer = MockData.customerEntity()

        when:
        service.deleteCustomer(id)

        then:
        1 * repository.findByIdAndActiveTrue(id) >> Optional.of(customer)
        1 * repository.save(customer) >> customer
    }

    def "delete customer - customer not found exception"() {
        given:
        def id = 1L

        when:
        service.deleteCustomer(id)

        then:
        1 * repository.findByIdAndActiveTrue(id) >> Optional.empty()
        def ex = thrown(CustomerNotFoundException)
        ex.errorCode == ErrorCodes.CUSTOMER_NOT_FOUND
        ex.message == "customer not found exception"
    }
}
