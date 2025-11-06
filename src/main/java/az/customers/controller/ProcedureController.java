package az.customers.controller;

import az.customers.model.dto.AccountsTransactionDto;
import az.customers.model.response.CommonResponse;
import az.customers.service.ProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{accountId}/procedures")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureService procedureService;

    @PostMapping
    public CommonResponse<AccountsTransactionDto.Response> createAccountTransaction(
            @PathVariable Long accountId, @RequestBody AccountsTransactionDto.Request request) {
        return CommonResponse.success(
                "Account transaction successfully created", procedureService.response(accountId, request));
    }

    @GetMapping("{transactionId}")
    public CommonResponse<AccountsTransactionDto.Response>
    getAccountTransaction(@PathVariable Long transactionId,
                          @PathVariable Long accountId) {
        return CommonResponse.success(
                "Successfully", procedureService.getTransactionById(accountId, transactionId));

    }
}
