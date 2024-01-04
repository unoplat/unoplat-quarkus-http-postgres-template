package org.demo.repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.demo.models.ProductHealth;
import org.demo.models.ProductHealth$;
import org.demo.models.generic.PaginatedResult;
import org.demo.repository.abstraction.ProductHealthRepositoryInterface;
import com.speedment.jpastreamer.application.JPAStreamer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductHealthRepository implements PanacheRepository<ProductHealth>,ProductHealthRepositoryInterface {
    
    @Inject
    JPAStreamer jpaStreamer;

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
  

    /**
     * Saves the given ProductHealth entity.
     *
     * @param  entity  the ProductHealth entity to be saved
     * @return         the saved ProductHealth entity
     */
    
    public void persistProductHealth(ProductHealth entity) {

         this.persist(entity);
    }

    @Override
    public PaginatedResult<ProductHealth> findProductHealth(int minHealth,int page, int size) {
        // TODO Auto-generated method stub
        Stream<ProductHealth> stream = jpaStreamer.stream(ProductHealth.class)
                                        .filter(ProductHealth$.healthScore.greaterOrEqual(minHealth));

        // Pagination
        List<ProductHealth> items = stream.skip((long) page * size)
                                          .limit(size)
                                          .collect(Collectors.toList());
        
                                        
       // For total count, apply the same filter but without pagination
        long totalItems = jpaStreamer.stream(ProductHealth.class)
                                     .filter(ProductHealth$.healthScore.greaterOrEqual(minHealth))
                                     .count();
      
        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new PaginatedResult<>(items, page, size, totalItems, totalPages);
    }

}
