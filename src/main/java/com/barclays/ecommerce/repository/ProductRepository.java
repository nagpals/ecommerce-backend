package com.barclays.ecommerce.repository;


import com.barclays.ecommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ 'title' : { $regex: ?0 }}")
    List<Product> findProductByRegexpTitle(String regexp);

}

