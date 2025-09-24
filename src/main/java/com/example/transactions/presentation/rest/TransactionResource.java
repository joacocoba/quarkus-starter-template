package com.example.transactions.presentation.rest;

import java.net.URI;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.example.transactions.application.dto.TransactionQuery;
import com.example.transactions.application.usecases.CreateTransactionUseCase;
import com.example.transactions.application.usecases.GetTransactionUseCase;
import com.example.transactions.application.usecases.ListTransactionsUseCase;
import com.example.transactions.domain.model.Transaction;
import com.example.transactions.presentation.dto.CreateTransactionRequest;
import com.example.transactions.presentation.dto.TransactionListResponse;
import com.example.transactions.presentation.dto.TransactionResponse;
import com.example.transactions.shared.constants.ApiConstants;

/**
 * REST API for transaction operations.
 *
 * <p>This resource provides endpoints for creating, retrieving, and listing transactions following
 * RESTful principles.
 */
@Path(ApiConstants.TRANSACTIONS_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Transactions", description = "Transaction management operations")
public class TransactionResource {

  private final CreateTransactionUseCase createTransactionUseCase;
  private final GetTransactionUseCase getTransactionUseCase;
  private final ListTransactionsUseCase listTransactionsUseCase;

  @Inject
  public TransactionResource(
      CreateTransactionUseCase createTransactionUseCase,
      GetTransactionUseCase getTransactionUseCase,
      ListTransactionsUseCase listTransactionsUseCase) {
    this.createTransactionUseCase = createTransactionUseCase;
    this.getTransactionUseCase = getTransactionUseCase;
    this.listTransactionsUseCase = listTransactionsUseCase;
  }

  @POST
  @Operation(
      summary = "Create a new transaction",
      description = "Creates a new pending transaction")
  @APIResponses({
    @APIResponse(
        responseCode = "201",
        description = "Transaction created successfully",
        content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
    @APIResponse(responseCode = "400", description = "Invalid request data"),
    @APIResponse(responseCode = "500", description = "Internal server error")
  })
  public Response createTransaction(@Valid CreateTransactionRequest request) {
    Transaction transaction =
        createTransactionUseCase.execute(
            request.amount(),
            request.currency(),
            request.originAccountNumber(),
            request.destinationAccountNumber());
    TransactionResponse response = TransactionResponse.fromDomain(transaction);

    URI location = URI.create(ApiConstants.TRANSACTIONS_PATH + "/" + transaction.getId());
    return Response.created(location).entity(response).build();
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Get transaction by ID", description = "Retrieves a specific transaction")
  @APIResponses({
    @APIResponse(
        responseCode = "200",
        description = "Transaction found",
        content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
    @APIResponse(responseCode = "404", description = "Transaction not found"),
    @APIResponse(responseCode = "500", description = "Internal server error")
  })
  public Response getTransaction(
      @Parameter(description = "Transaction ID", required = true) @PathParam("id") String id) {
    Transaction transaction = getTransactionUseCase.execute(id);
    TransactionResponse response = TransactionResponse.fromDomain(transaction);
    return Response.ok(response).build();
  }

  @GET
  @Operation(
      summary = "List transactions",
      description = "Retrieves a paginated list of transactions")
  @APIResponses({
    @APIResponse(
        responseCode = "200",
        description = "Transactions retrieved successfully",
        content = @Content(schema = @Schema(implementation = TransactionListResponse.class))),
    @APIResponse(responseCode = "400", description = "Invalid query parameters"),
    @APIResponse(responseCode = "500", description = "Internal server error")
  })
  public Response listTransactions(
      @Parameter(description = "Number of records to skip")
          @QueryParam("offset")
          @DefaultValue("0")
          @Min(0) int offset,
      @Parameter(description = "Maximum number of records to return")
          @QueryParam("limit")
          @DefaultValue("20")
          @Min(1) @Max(100) int limit) {

    TransactionQuery query = new TransactionQuery(offset, limit);
    List<Transaction> transactions = listTransactionsUseCase.execute(query);
    long totalCount = listTransactionsUseCase.getTotalCount();

    List<TransactionResponse> responses =
        transactions.stream().map(TransactionResponse::fromDomain).toList();

    TransactionListResponse response =
        TransactionListResponse.of(responses, totalCount, offset, limit);
    return Response.ok(response).build();
  }
}
