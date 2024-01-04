package org.demo.service;

import java.util.Map;
import org.demo.models.ProductHealth;
import org.demo.models.generic.PaginatedResult;
import org.demo.repository.abstraction.ProductHealthRepositoryInterface;
import org.demo.service.abstraction.ProductHealthServiceInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductHealthService implements ProductHealthServiceInterface{

    @Inject
    ProductHealthRepositoryInterface productHealthRepositoryInterface;


    @Override
    public void saveProductHealth(ProductHealth entity) {
        // TODO Auto-generated method stub
         productHealthRepositoryInterface.persistProductHealth(entity);
    }

    @Override
    public PaginatedResult<ProductHealth> findProductsByMinHealthScore(int minHealthScore, int page, int size
           ) {
        // TODO Auto-generated method stub
 
        return productHealthRepositoryInterface.findProductHealth(minHealthScore,page,size);
    }
}

