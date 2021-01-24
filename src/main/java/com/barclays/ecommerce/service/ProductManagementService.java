package com.barclays.ecommerce.service;

import com.barclays.ecommerce.model.Cart;
import com.barclays.ecommerce.model.Product;
import com.barclays.ecommerce.repository.CartRepository;
import com.barclays.ecommerce.repository.ProductRepository;
import com.barclays.ecommerce.util.ECommerceConstants;
import com.barclays.ecommerce.util.ErrorCodes;
import com.barclays.ecommerce.util.GenericLogger;
import com.barclays.ecommerce.view.ECommerceResponse;
import com.barclays.ecommerce.view.GetProductsResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 *
 */
@Component
public class ProductManagementService {

    private static final Logger logger = LogManager.getLogger(ProductManagementService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    /**
     * @param uuid
     * @param productId
     * @return
     */
    public GetProductsResponse getProduct(String uuid, String productId) {
        GetProductsResponse response = new GetProductsResponse();
        List<Product> productList = new ArrayList<>();
        try {
            // Loading the product details from external service
            long productCount = productRepository.count();
            if (productCount <= 0) {
                loadDataIntoDatabase(uuid);
            }
            if (productId.equalsIgnoreCase("all")) {
                productList = productRepository.findAll();
                response.setProducts(productList);
            } else {
                Optional<Product> productOptional = productRepository.findById(productId);
                if (productOptional.isPresent()) {
                    Product product = productOptional.get();
                    productList.add(product);
                    response.setProducts(productList);
                }
            }
            GenericLogger.logResponse(logger, uuid, "SUCCESS", productList);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setResponseMessage(ECommerceConstants.API_PROCESSED_FAILURE);
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.API_PROCESSED_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return response;
    }

    /**
     * @param uuid
     */
    private void loadDataIntoDatabase(String uuid) {
        try {
            JSONArray jsonArray = fetchProductsFromOtherSource();
            for (int i = 0; i < jsonArray.size(); i++) {
                Map<String, Object> linkedHashMap = new LinkedHashMap((Map) jsonArray.get(i));
                Product product = new Product();
                product.setBookID(String.valueOf((Object) linkedHashMap.get("bookID")));
                product.setTitle(String.valueOf((Object) linkedHashMap.get("title")));
                product.setAuthors(String.valueOf((Object) linkedHashMap.get("authors")));
                product.setAverage_rating(String.valueOf((Object) linkedHashMap.get("average_rating")));
                product.setIsbn(String.valueOf((Object) linkedHashMap.get("isbn")));
                product.setLanguage_code(String.valueOf((Object) linkedHashMap.get("language_code")));
                product.setRatings_count(String.valueOf((Object) linkedHashMap.get("ratings_count")));
                product.setPrice(String.valueOf((Object) linkedHashMap.get("price")));
                // TODO : Defulating availability count to 2 for all the books
                product.setAvailableCount(2l);
                productRepository.save(product);
            }
        } catch (
                Exception e) {
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.STORING_PRODUCT_FAILURE);
            logger.debug(ECommerceConstants.STORING_PRODUCT_FAILURE + " : " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return
     */
    private JSONArray fetchProductsFromOtherSource() {
        final String uri = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/books8f8fe52.json";
        RestTemplate restTemplate = new RestTemplate();
        JSONArray totalProducts = restTemplate.getForObject(uri, JSONArray.class);
        return totalProducts;
    }

    /**
     * @param uuid
     * @param jsonObject
     * @return
     */
    public ECommerceResponse addProductIntoCart(String uuid, JSONObject jsonObject) {
        ECommerceResponse response = new ECommerceResponse();
        try {
            String productId = (String) jsonObject.get("productId");
            String userId = (String) jsonObject.get("userId");
            String id = String.join("-", userId, String.valueOf(productId));
            Optional<Cart> cartOptional = cartRepository.findById(id);
            if (cartOptional.isPresent()) {
                // returning error response if already record exits
                response.setResponseMessage(ECommerceConstants.RECORD_ALREADY_EXIST_IN_CART);
                response.setSuccess(false);
                GenericLogger.logResponse(logger, uuid, "SUCCESS", ErrorCodes.E_COMM_01_DESC);
            } else {
                Optional<Product> productOptional = productRepository.findById(productId);
                if (productOptional.isPresent() && productOptional.get().getAvailableCount() > 0) {
                    Cart cart = new Cart(productId, userId);
                    cartRepository.save(cart);
                    response.setResponseMessage(ECommerceConstants.RECORD_ADD_TO_CART);
                    response.setSuccess(true);
                    GenericLogger.logResponse(logger, uuid, "SUCCESS", ECommerceConstants.CREATE_RECORD_SUCCESS);
                } else {
                    response.setResponseMessage(ECommerceConstants.PRODUCT_NOT_AVAILABLE);
                    response.setSuccess(false);
                    GenericLogger.logResponse(logger, uuid, "SUCCESS", ECommerceConstants.PRODUCT_NOT_AVAILABLE);
                }
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setResponseMessage(ECommerceConstants.CREATE_RECORD_FAILURE);
            // Logger error response
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.CREATE_RECORD_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return response;
    }

    /**
     * @param uuid
     * @return
     */
    public GetProductsResponse getProductFromCart(String uuid, String userId) {
        GetProductsResponse productsResponse = new GetProductsResponse();
        try {
            List<Cart> cartList = cartRepository.findByUserId(userId);
            if (cartList == null || cartList.isEmpty()) {
                productsResponse.setResponseMessage(ECommerceConstants.CART_IS_EMPTY);
                productsResponse.setSuccess(false);
                GenericLogger.logResponse(logger, uuid, "FAILURE", ECommerceConstants.CART_IS_EMPTY);
            } else {
                List<Product> productList = new ArrayList<>();
                for (Cart cart : cartList) {
                    Optional<Product> productOptional = productRepository.findById(cart.getProductId());
                    if (productOptional.isPresent()) {
                        Product product = productOptional.get();
                        productList.add(product);
                    }
                }
                productsResponse.setProducts(productList);
                productsResponse.setSuccess(true);
                GenericLogger.logResponse(logger, uuid, "SUCCESS", productsResponse);
            }
        } catch (Exception e) {
            productsResponse.setResponseMessage(ECommerceConstants.API_PROCESSED_FAILURE);
            productsResponse.setSuccess(false);
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.API_PROCESSED_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return productsResponse;
    }

    /**
     * @param uuid
     * @return
     */
    public GetProductsResponse getProductsSortedByRating(String uuid) {
        GetProductsResponse response = new GetProductsResponse();
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "average_rating");
            List<Product> productList = productRepository.findAll(sort);
            response.setProducts(productList);
            response.setSuccess(true);
            GenericLogger.logResponse(logger, uuid, "SUCCESS", response);
        } catch (Exception e) {
            response.setResponseMessage(ECommerceConstants.API_PROCESSED_FAILURE);
            response.setSuccess(false);
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.API_PROCESSED_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return response;
    }

    /**
     * @param uuid
     * @param title
     * @return
     */
    public GetProductsResponse getProductByTitle(String uuid, String title) {
        GetProductsResponse response = new GetProductsResponse();
        try {

            String regex = "/"+title+"/";
            List<Product> productList = productRepository.findProductByRegexpTitle(regex);
            if (productList != null) {
                response.setProducts(productList);
            } else {
                response.setProducts(new ArrayList<>());
            }
            response.setSuccess(true);
            GenericLogger.logResponse(logger, uuid, "SUCCESS", response);
        } catch (Exception e) {
            response.setResponseMessage(ECommerceConstants.API_PROCESSED_FAILURE);
            response.setSuccess(false);
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.API_PROCESSED_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return response;
    }

    /**
     *
     * @param uuid
     * @param userId
     * @param productId
     * @return
     */
    public ECommerceResponse removeProductIntoCart(String uuid, String userId, String productId) {
        ECommerceResponse response = new ECommerceResponse();
        try {
            String id = String.join("-", userId, productId);
            Optional<Cart> cartOptional = cartRepository.findById(id);
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                cartRepository.delete(cart);
                response.setResponseMessage(ECommerceConstants.RECORD_REMOVED_SUCCESS);
                response.setSuccess(true);
            } else {
                // returning error response if  no record exits
                response.setResponseMessage(ErrorCodes.E_COMM_02_DESC);
                response.setSuccess(false);
                GenericLogger.logResponse(logger, uuid, "SUCCESS", ErrorCodes.E_COMM_02_DESC);
            }
        } catch (Exception e) {
            response.setResponseMessage(ECommerceConstants.API_PROCESSED_FAILURE);
            response.setSuccess(false);
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.API_PROCESSED_FAILURE);
            logger.debug(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return response;
    }
}
