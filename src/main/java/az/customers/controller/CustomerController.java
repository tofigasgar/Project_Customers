package az.customers.controller;

import az.customers.model.dto.CustomerDto;
import az.customers.model.response.CommonResponse;
import az.customers.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<Page<CustomerDto.Response>> getAllCustomers(
            @PageableDefault(sort = "id", size = 5) Pageable pageable) {
        return CommonResponse.success(
                "All users...", customerService.getAllCustomers(pageable));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<CustomerDto.Response> getCustomerById(@PathVariable Long id) {
        return CommonResponse.success(
                "Customer successfully ", customerService.getCustomerById(id));
    }

//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public CommonResponse<CustomerDto.Response> getCustomerByFin(@RequestParam String fin) {
//        return CommonResponse.success(
//                "Customer successfully ", customerService.getCustomerByFin(fin));
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<CustomerDto.Response> createCustomer(@RequestBody @Valid CustomerDto.Request request) {
        return CommonResponse.success(
                "Customer successfully created ", customerService.createCustomer(request));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<CustomerDto.Response> updateCustomer(
            @PathVariable Long id, @RequestBody @Valid CustomerDto.Update request) {
        return CommonResponse.success(
                "Customer successfully updated ", customerService.updateCustomer(id, request));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return CommonResponse.success("Customer successfully deleted", null);
    }
}
