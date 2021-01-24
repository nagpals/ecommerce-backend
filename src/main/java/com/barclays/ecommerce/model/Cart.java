package com.barclays.ecommerce.model;

import org.springframework.data.annotation.Id;

/**
 *
 */
public class Cart {

    @Id
    private String id;
    private String productId;
    private String userId;

    /**
     * @param productId
     * @param userId
     */
    public Cart(String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
        this.id = String.join("-", this.userId, this.productId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
