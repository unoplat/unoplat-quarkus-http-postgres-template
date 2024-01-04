package org.demo.controllers;


import org.demo.models.ProductHealth;
import org.demo.models.ProductHealthQueryDTO;
import org.demo.models.generic.PaginatedResult;
import org.demo.service.abstraction.ProductHealthServiceInterface;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.RestResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/product-health")
public class ProductHealthController  {


    @Inject
    private ProductHealthServiceInterface productHealthServiceInterface;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Create a new product health record", description = "Creates a new product health record and returns its details")
    @APIResponse(responseCode = "201", description = "Product health record created",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ProductHealth.class)))
    @RequestBody(description = "Product health data to create a new record",
                 required = true, content = @Content(schema = @Schema(implementation = ProductHealth.class)))
    public Response create(ProductHealth entity) {
        productHealthServiceInterface.saveProductHealth(entity);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Search for product health records", description = "Search for product health records by minimum health score with pagination and sorting options")
    @APIResponse(responseCode = "200", description = "List of product health records",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = PaginatedResult.class)))
    public RestResponse<PaginatedResult<ProductHealth>> findByMinHealthScore(
            ProductHealthQueryDTO searchParams) {
    
        PaginatedResult<ProductHealth> result = productHealthServiceInterface.findProductsByMinHealthScore(searchParams.getMinHealthScore(), searchParams.getPage(), searchParams.getSize());
        return RestResponse.ok(result);
    }

}

