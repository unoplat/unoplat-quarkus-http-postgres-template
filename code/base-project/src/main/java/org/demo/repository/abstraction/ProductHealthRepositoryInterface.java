package org.demo.repository.abstraction;

import java.util.Map;

import org.demo.models.ProductHealth;
import org.demo.models.generic.PaginatedResult;


public interface ProductHealthRepositoryInterface {

    public void persistProductHealth(ProductHealth entity);

    public PaginatedResult<ProductHealth> findProductHealth(int minHealth, int page, int size);

}
