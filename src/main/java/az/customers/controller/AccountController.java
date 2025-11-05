package az.customers.controller;

import az.customers.model.dto.AccountsDto;
import az.customers.model.response.CommonResponse;
import az.customers.service.AccountService;
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
    public CommonResponse<AccountsDto.Response> createAccount(
            @PathVariable Long customerId, @RequestBody @Valid AccountsDto.Request request) {
        return CommonResponse.success(
                "Account successfully created", accountService.createAccount(customerId, request));
    }


    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<AccountsDto.Response>
    updateAccount(@PathVariable Long accountId,
                  @PathVariable Long customerId, @RequestBody @Valid AccountsDto.Update request) {
        return CommonResponse.success(
                "Account successfully updated", accountService.updateAccount(accountId, customerId, request));
    }

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<AccountsDto.Response> getAccountById(
            @PathVariable Long customerId, @PathVariable Long accountId) {
        return CommonResponse.success(
                "Account successfully", accountService.getAccountById(customerId, accountId));
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<Void> deleteAccount(
            @PathVariable Long customerId, @PathVariable Long accountId) {
        accountService.deleteAccountById(customerId, accountId);
        return CommonResponse.success("Account successfully deleted ", null);
    }
}


