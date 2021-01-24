package com.barclays.ecommerce.repository;

import com.barclays.ecommerce.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    @Query("{ 'userId' : ?0 }")
    List<Cart> findByUserId(String userId);

}
