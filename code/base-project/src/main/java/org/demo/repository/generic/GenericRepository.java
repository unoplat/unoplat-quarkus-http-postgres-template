package org.demo.repository.generic;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.demo.models.generic.PaginatedResult;
import org.demo.models.generic.SortCriteria;

/**
 * Abstract base repository providing common data access functionality.
 * Can be extended to create type-specific repositories.
 *
 * @param <T> the type of the entity
 * @param <ID> the type of the primary key of the entity
 */
public abstract class GenericRepository<T, ID> {

    private Class<T> entityClass;

    @Inject
    EntityManager entityManager;

    /**
     * Constructor for the repository, requires the entity class.
     * This class type is used for entity management operations.
     *
     * @param entityClass the class type of the repository entity
     * Purpose: Sets the entity class, which is used in various operations.  
     */
    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Persists the given entity in the database.
     * 
     * @param entity the entity to persist
     * @return the persisted entity
     * Purpose: Persists the given entity in the database.
     */
    @Transactional
    public T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    /**
     * Retrieves an entity by its primary key.
     *
     * @param id the primary key
     * @return an Optional containing the found entity or empty if not found
     * Purpose: Retrieves an entity by its primary key.
     */
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    /**
     * Updates the given entity in the database.
     *
     * @param entity the entity to update
     * @return the updated entity
     * Purpose: Merges the state of the given entity into the current persistence context.
     */
    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    /**
     * Deletes the given entity from the database.
     *
     * @param entity the entity to delete
     * Purpose: Deletes the given entity from the database.
     */
    @Transactional
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    /**
     * Provides a CriteriaBuilder for building criteria queries.
     * Useful for creating type-safe dynamic queries.
     * Purpose: Provides a CriteriaBuilder instance for constructing CriteriaQuery objects.
     * @return an instance of CriteriaBuilder
     */
    public CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    /**
     * Executes a criteria query and returns the result list.
     *
     * @param criteria the criteria query to execute
     * @return a list of entities matching the criteria
     * Purpose: Provides a CriteriaBuilder instance for constructing CriteriaQuery objects.
     */
    public List<T> findByCriteria(CriteriaQuery<T> criteria) {
        return entityManager.createQuery(criteria).getResultList();
    }

    /**
     * Creates a CriteriaQuery for the entity type.
     * This is a starting point for building custom criteria queries.
     * 
     * @return a new instance of CriteriaQuery
     * Purpose: Creates and returns a CriteriaQuery instance for the entity type T.
     */
    public CriteriaQuery<T> createCriteriaQuery() {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        return cq;
    }

    /**
     * Executes a JPQL query with parameters and returns the result list.
     * 
     * @param jpql the JPQL query string
     * @param parameters a map of parameters for the query
     * @return a list of entities resulting from the query
     * Purpose: Executes a JPQL query, which is a portable, database-independent query language defined by JPA.
     */
    public List<T> findByJPQL(String jpql, Map<String, Object> parameters) {
        var query = entityManager.createQuery(jpql, entityClass);
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }
    /**
     * Executes a native SQL query with parameters and returns the result list.
     *
     * @param sql the native SQL query string
     * @param parameters a map of parameters for the query
     * @return a list of entities resulting from the query
     * Purpose: Executes a native SQL query, which can be useful for leveraging database-specific features or for queries that are difficult to express in JPQL.
     */
    public List<T> findByNativeQuery(String sql, Map<String, Object> parameters) {
        var query = entityManager.createNativeQuery(sql, entityClass);
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }

    public PaginatedResult<T> findAll(int page, int size, Map<String, Object> filters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
    
        // Combining Filter Conditions
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((field, value) -> predicates.add(cb.equal(root.get(field), value)));
        if (!predicates.isEmpty()) {
            criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        } 
    
        // Execute Query
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<T> items = query.getResultList();
    
        // Count Total Items
        long totalItems = countTotalItems(filters, cb, root);
    
        return new PaginatedResult<>(items, page, size, totalItems);
    }

    public PaginatedResult<T> findAll(int page, int size, Map<String, Object> filters, SortCriteria sortCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
    
        // Combining Filter Conditions
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((field, value) -> predicates.add(cb.equal(root.get(field), value)));
        if (!predicates.isEmpty()) {
            criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        }
    
        // Apply Sorting
        if (sortCriteria != null && sortCriteria.getSortCriteria() != null) {
            List<Order> orders = new ArrayList<>();
            for (Map.Entry<String, String> entry : sortCriteria.getSortCriteria().entrySet()) {
                String fieldName = entry.getKey();
                String direction = entry.getValue();
                Path<Object> sortField = root.get(fieldName);
        
                if ("desc".equalsIgnoreCase(direction)) {
                    orders.add(cb.desc(sortField));
                } else {
                    orders.add(cb.asc(sortField));
                }
            }
            criteriaQuery.orderBy(orders);
        }
        
    
        // Execute Query
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<T> items = query.getResultList();
    
        // Count Total Items
        long totalItems = countTotalItems(filters, cb, root);
    
        return new PaginatedResult<>(items, page, size, totalItems);
    }
    
    private long countTotalItems(Map<String, Object> filters, CriteriaBuilder cb, Root<T> root) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
    
        // Combining Filter Conditions
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((field, value) -> predicates.add(cb.equal(countRoot.get(field), value)));
        if (!predicates.isEmpty()) {
            countQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        }
    
        countQuery.select(cb.count(countRoot));
    
        return entityManager.createQuery(countQuery).getSingleResult();
    }
    

 
 
}
