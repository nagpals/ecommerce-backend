package com.barclays.ecommerce.service;

import com.barclays.ecommerce.model.Product;
import com.barclays.ecommerce.repository.CartRepository;
import com.barclays.ecommerce.repository.ProductRepository;
import com.barclays.ecommerce.util.ECommerceConstants;
import com.barclays.ecommerce.view.ECommerceResponse;
import com.barclays.ecommerce.view.GetProductsResponse;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@SpringBootTest
@ActiveProfiles("tests")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductManagementServiceTest {


    @Autowired
    ProductManagementService productManagementService = new ProductManagementService();

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    private final String uuid = "572897-2783843";
    /**
     * getProductTest
     */
    @Test
    public void getProductTest() {

        try {
            setMockProductInDB("6");
            GetProductsResponse response = productManagementService.getProduct(uuid,"6");
            Assert.assertEquals("6",response.getProducts().get(0).getBookID());

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * addProductIntoCart
     */
    @Test
    public void addProductIntoCartWithoutProduct() {
       try{
           JSONObject jsonObject = new JSONObject();
           jsonObject.put("productId", "62372");
           jsonObject.put("userId", "31122");
           ECommerceResponse response = productManagementService.addProductIntoCart(uuid,jsonObject);
           Assert.assertEquals(ECommerceConstants.PRODUCT_NOT_AVAILABLE,response.getResponseMessage());
       } catch (Exception e) {
           Assert.fail(e.getMessage());
       }
    }

    /**
     * addProductIntoCart
     */
    @Test
    public void addProductIntoCartWithProduct() {
        try{
            // Mock
            setMockProductInDB("62372");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId", "62372");
            jsonObject.put("userId", "31122");
            ECommerceResponse response = productManagementService.addProductIntoCart(uuid,jsonObject);
            Assert.assertEquals(ECommerceConstants.RECORD_ADD_TO_CART,response.getResponseMessage());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    /**
     *
     * @param productId
     */
    private void setMockProductInDB(String productId) {
        // mock values product table
        Product product = new Product();
        product.setAvailableCount(3l);
        product.setBookID(productId);
        product.setLanguage_code("eng");
        product.setAuthors("J.K. Rowling-Mary GrandPre");
        product.setAverage_rating("4.56");
        product.setIsbn("439785960");
        product.setLanguage_code("language_code");
        product.setRatings_count("6578389");
        product.setPrice("238");
        product.setTitle("Harry Potter and the Half-Blood Prince (Harry Potter  #6)");
        productRepository.save(product);
    }

    @After
    public void removeDataFromDatabase() {
        productRepository.deleteAll();
        cartRepository.deleteAll();
    }
}
