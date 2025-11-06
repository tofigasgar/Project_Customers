package az.customers.controller;

import az.customers.model.dto.AccountsTransactionDto;
import az.customers.model.response.CommonErrorResponse;
import az.customers.model.response.CommonResponse;
import az.customers.service.ProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{accountId}/procedures")
@RequiredArgsConstructor
public class ProcedureController {

    private final ProcedureService procedureService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create transaction by procedure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Transaction successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountsTransactionDto.Response.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<AccountsTransactionDto.Response> createAccountTransaction(
            @PathVariable Long accountId, @RequestBody AccountsTransactionDto.Request request) {
        return CommonResponse.success(
                "Account transaction successfully created", procedureService.response(accountId, request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{transactionId}")
    @Operation(summary = "Get Transaction by id (Procedure)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Transaction successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountsTransactionDto.Response.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    public CommonResponse<AccountsTransactionDto.Response>
    getAccountTransaction(@PathVariable Long transactionId,
                          @PathVariable Long accountId) {
        return CommonResponse.success(
                "Successfully", procedureService.getTransactionById(accountId, transactionId));
    }
}
