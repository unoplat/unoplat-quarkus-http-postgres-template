package org.demo.service;

import java.util.Map;
import org.demo.models.ProductHealth;
import org.demo.models.generic.PaginatedResult;
import org.demo.models.generic.SortCriteria;
import org.demo.repository.abstraction.ProductHealthRepositoryInterface;
import org.demo.service.abstraction.ProductHealthServiceInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductHealthService implements ProductHealthServiceInterface{

    @Inject
    ProductHealthRepositoryInterface productHealthRepositoryInterface;


    @Override
    public ProductHealth saveProductHealth(ProductHealth entity) {
        // TODO Auto-generated method stub
        return productHealthRepositoryInterface.saveProductHealth(entity);
    }

    @Override
    public PaginatedResult<ProductHealth> findProductsByMinHealthScore(int minHealthScore, int page, int size,
            Map<String, String> sortBy) {
        // TODO Auto-generated method stub

        Map<String, Object> filters = Map.of("healthScore", minHealthScore);
        SortCriteria sortCriteria = new SortCriteria(sortBy);
        
        return productHealthRepositoryInterface.findProductHealth(page, size, filters, sortCriteria);
    }
}

