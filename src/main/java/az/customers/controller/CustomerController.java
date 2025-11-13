package az.customers.controller;

import az.customers.model.dto.CustomerDto;
import az.customers.model.response.CommonErrorResponse;
import az.customers.model.response.CommonResponse;
import az.customers.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    @Operation(summary = "All customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Customers list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.Response.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<Page<CustomerDto.Response>> getAllCustomers(
            @ParameterObject @PageableDefault(sort = "id", size = 5) Pageable pageable) {
        return CommonResponse.success(customerService.getAllCustomers(pageable));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get customer by id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.Response.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Customer Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<CustomerDto.Response> getCustomerById(@PathVariable Long id) {
        return CommonResponse.success(customerService.getCustomerById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Customer successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.Response.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<CustomerDto.Response> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, content = @Content(schema = @Schema(implementation = CustomerDto.Request.class)))
            @RequestBody @Valid CustomerDto.Request request) {
        return CommonResponse.success(customerService.createCustomer(request));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Customer updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Customer successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.Response.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<CustomerDto.Response> updateCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, content = @Content(schema = @Schema(implementation = CustomerDto.Update.class)))
            @PathVariable Long id, @RequestBody @Valid CustomerDto.Update request) {
        return CommonResponse.success(customerService.updateCustomer(id, request));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Customer deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Customer successfully deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.Response.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return CommonResponse.success("Customer successfully deleted", null);
    }
}
