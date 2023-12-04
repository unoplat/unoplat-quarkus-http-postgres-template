package org.demo.repository.abstraction;

import java.util.Map;

import org.demo.models.ProductHealth;
import org.demo.models.generic.PaginatedResult;
import org.demo.models.generic.SortCriteria;

public interface ProductHealthRepositoryInterface {

    public ProductHealth saveProductHealth(ProductHealth entity);

    public PaginatedResult<ProductHealth> findProductHealth(int page, int size, Map<String, Object> filters, SortCriteria sortCriteria);

    
}
