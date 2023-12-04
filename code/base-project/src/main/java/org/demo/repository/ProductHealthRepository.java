package org.demo.repository;

import java.util.HashMap;
import java.util.Map;

import org.demo.models.ProductHealth;
import org.demo.models.generic.PaginatedResult;
import org.demo.models.generic.SortCriteria;
import org.demo.repository.abstraction.ProductHealthRepositoryInterface;
import org.demo.repository.generic.GenericRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductHealthRepository extends GenericRepository<ProductHealth, Long>
        implements ProductHealthRepositoryInterface {

    public ProductHealthRepository() {
        super(ProductHealth.class);
        // TODO Auto-generated constructor stub
    }

    /**
     * Finds products by a minimum health score, with pagination and sorting.
     *
     * @param minHealthScore The minimum health score to filter by.
     * @param page           The page number (0-indexed).
     * @param size           The number of records per page.
     * @param sortBy         A map of attribute names and sort directions for
     *                       sorting.
     * @return A paginated result of products matching the criteria.
     */
    public PaginatedResult<ProductHealth> findProductHealth(int page, int size, Map<String, Object> filters,
            SortCriteria sortCriteria)

    {

        return findAll(page, size, filters, sortCriteria);

    }

    /**
     * Saves the given ProductHealth entity.
     *
     * @param  entity  the ProductHealth entity to be saved
     * @return         the saved ProductHealth entity
     */
    public ProductHealth saveProductHealth(ProductHealth entity) {

        return saveProductHealth(entity);
    }

}
