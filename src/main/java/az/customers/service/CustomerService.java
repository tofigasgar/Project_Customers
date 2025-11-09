package az.customers.service;

import az.customers.exception.CustomerNotFoundException;
import az.customers.exception.ResourceAlreadyExistsException;
import az.customers.logger.DPLogger;
import az.customers.model.dto.CustomerDto;
import az.customers.model.entity.Customer;
import az.customers.model.enums.ErrorCodes;
import az.customers.repository.CustomerRepository;
import az.customers.util.mapping.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final DPLogger logger = DPLogger.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Page<CustomerDto.Response> getAllCustomers(Pageable pageable) {
        logger.info("Get all customers");
        var customers = customerRepository.findByActiveTrue(pageable);
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "customer not found exception");
        }
        logger.info("All customers successfully found");
        return customers.map(customerMapper::toResponse);
    }

    public CustomerDto.Response getCustomerById(Long id) {
        logger.info("Get customer by id: {}", id);
        var customer = findById(id);
        logger.info("Customer successfully found by id: " + id);
        return customerMapper.toResponse(customer);
    }

    @Transactional
    public CustomerDto.Response createCustomer(CustomerDto.Request request) {
        logger.info("Customer creating beginning....");
        if (customerRepository.existsByFin(request.getFin())) {
            logger.warn("Customer already exists with fin: {}", request.getFin());
            throw new ResourceAlreadyExistsException(ErrorCodes.FIN_ALREADY_EXISTS, "fin already exists exception");
        }
        if (customerRepository.existsByPassportSerial(request.getPassportSerial())) {
            logger.warn("passportSerial already exists: {}", request.getPassportSerial());
            throw new ResourceAlreadyExistsException(ErrorCodes.PASSPORT_SERIAL_EXISTS, "passportSerial already exists exception");
        }
        var customer = customerRepository.save(customerMapper.toEntity(request));
        logger.info("Customer successfully created customer with id={}, fin = {}", customer.getId(), customer.getFin());
        return customerMapper.toResponse(customer);
    }

    @Transactional
    public CustomerDto.Response updateCustomer(Long id, CustomerDto.Update request) {
        logger.info("Customer updating beginning....");
        var customer = findById(id);
        if (customer.getPassportSerial().equals(request.getPassportSerial())) {
            logger.warn("passportSerial already exists: {}", request.getPassportSerial());
            throw new ResourceAlreadyExistsException(ErrorCodes.PASSPORT_SERIAL_EXISTS, "passportSerial already exists exception");
        }
        logger.info("Customer updating with id: {}", id);
        customerMapper.updateEntityFromRequest(request, customer);
        var updatedCustomer = customerRepository.save(customer);
        logger.info("Customer successfully updated customer with id={}, fin = {}", customer.getId(), customer.getFin());
        return customerMapper.toResponse(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        logger.info("Customer deleting beginning....");
        var customer = findById(id);
        logger.info("Customer deleting with id: {}", id);
        customer.setActive(false);
        customerRepository.save(customer);
        logger.info("Customer successfully deleted customer with id={}, fin = {}", customer.getId(), customer.getFin());
        logger.info("Customer deleting finished....");
    }

    public Customer findById(Long id) {
        return customerRepository.findByIdAndActiveTrue(id).orElseThrow(() -> {
            logger.warn("Customer not found with id: {}", id);
            return new CustomerNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND, "customer not found exception");
        });
    }
}
