package org.demo.service.abstraction;

import java.util.Map;

import org.demo.models.ProductHealth;
import org.demo.models.generic.PaginatedResult;

public interface ProductHealthServiceInterface {

    public ProductHealth saveProductHealth(ProductHealth entity);

    public PaginatedResult<ProductHealth> findProductsByMinHealthScore(int minHealthScore, int page, int size, Map<String, String> sortBy);
    
}
