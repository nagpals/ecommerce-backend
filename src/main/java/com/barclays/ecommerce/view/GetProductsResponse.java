package com.barclays.ecommerce.view;

import com.barclays.ecommerce.model.Product;

import java.util.List;

/**
 *
 */
public class GetProductsResponse extends ECommerceResponse {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
