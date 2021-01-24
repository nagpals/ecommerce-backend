package com.barclays.ecommerce.repository;

import com.barclays.ecommerce.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * UserRepository
 *
 * @author subash
 */
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{emailId : ?0}")
    User findUserByEmailId(String emailId);

    @Query("{phoneNo : ?0}")
    User findUserByPhoneNo(String phoneNo);

    @Query("{$or : [{emailId: ?0}, {phoneNo : ?1}]}")
    User findUserByEmailIdOrPhoneNo(String emailId, String phoneNo);
}
