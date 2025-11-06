package az.customers.controller;

import az.customers.model.dto.AccountsDto;
import az.customers.model.response.CommonErrorResponse;
import az.customers.model.response.CommonResponse;
import az.customers.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{customerId}/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create account for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Account successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountsDto.Response.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<AccountsDto.Response> createAccount(
            @PathVariable Long customerId, @RequestBody @Valid AccountsDto.Request request) {
        return CommonResponse.success(
                "Account successfully created", accountService.createAccount(customerId, request));
    }


    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update account for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Account successfully updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountsDto.Response.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<AccountsDto.Response>
    updateAccount(@PathVariable Long accountId,
                  @PathVariable Long customerId, @RequestBody @Valid AccountsDto.Update request) {
        return CommonResponse.success(
                "Account successfully updated", accountService.updateAccount(accountId, customerId, request));
    }


    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get accounts by id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountsDto.Response.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<AccountsDto.Response> getAccountById(
            @PathVariable Long customerId, @PathVariable Long accountId) {
        return CommonResponse.success(
                "Account successfully", accountService.getAccountById(customerId, accountId));
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get accounts by id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountsDto.Response.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<Void> deleteAccount(
            @PathVariable Long customerId, @PathVariable Long accountId) {
        accountService.deleteAccountById(customerId, accountId);
        return CommonResponse.success("Account successfully deleted ", null);
    }
}


