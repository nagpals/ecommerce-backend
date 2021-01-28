package com.barclays.ecommerce.controller;

import com.barclays.ecommerce.service.OrderManagementService;
import com.barclays.ecommerce.service.ProductManagementService;
import com.barclays.ecommerce.service.UserManagementService;
import com.barclays.ecommerce.util.ECommerceConstants;
import com.barclays.ecommerce.util.GenericLogger;
import com.barclays.ecommerce.view.ECommerceResponse;
import com.barclays.ecommerce.view.GetProductsResponse;
import com.barclays.ecommerce.view.GetUserResponse;
import com.barclays.ecommerce.view.UserView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ECommerceController
 *
 * @author subash
 */
@Controller
@RequestMapping("/barclays")
public class ECommerceController {

    private static final Logger logger = LogManager.getLogger(ECommerceController.class);

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private ProductManagementService productManagementService;

    @Autowired
    private OrderManagementService orderManagementService;
    /**
     * Get Version
     *
     * @return
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public String getVersion() {
        return "0.1";
    }

    /**
     * Create user
     *
     * @param userView
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ECommerceResponse createUser(@RequestBody UserView userView) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.CREATE_USER, "POST", userView);
        return userManagementService.createUser(uuid, userView);
    }

    /**
     * get user
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public GetUserResponse createUser(@PathVariable(value = "userId") String userId) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.GET_USER, "GET", userId);
        return userManagementService.getUser(uuid, userId);
    }

    /**
     * get logId, can be either email or phone No.
     *
     * @param logId
     * @return
     */
    @RequestMapping(value = "/logIn/{logId}", method = RequestMethod.GET)
    @ResponseBody
    public GetUserResponse getLoggedIn(@PathVariable("logId") String logId) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.GET_LOGGED_IN, "GET", logId);
        return userManagementService.getLoggedIn(uuid, logId);
    }

    /**
     * fetch products
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public GetProductsResponse getProduct(@PathVariable("productId") String productId) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.GET_PRODUCTS, "GET", productId);
        return productManagementService.getProduct(uuid, productId);
    }

    /**
     * addProductIntoCart
     *
     * @param jsonObject
     * @return
     */
   // @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    @ResponseBody
    public ECommerceResponse addProductIntoCart(@RequestBody JSONObject jsonObject) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.ADD_CART, "GET", jsonObject);
        return productManagementService.addProductIntoCart(uuid, jsonObject);
    }

    /**
     * fetch products from cart
     *
     * @return
     */
    @RequestMapping(value = "/cart/all/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public GetProductsResponse getProductFromCart(@PathVariable("userId") String userId) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.GET_CART, "GET", null);
        return productManagementService.getProductFromCart(uuid,userId);
    }

    /**
     * fetch products sorted by average rating
     *
     * @return
     */
    @RequestMapping(value = "/product/sorted/all", method = RequestMethod.GET)
    @ResponseBody
    public GetProductsResponse getProduct() {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.GET_PRODUCTS, "GET", null);
        return productManagementService.getProductsSortedByRating(uuid);
    }

    /**
     * fetch products sorted by average rating
     *
     * @return
     */
    @RequestMapping(value = "/product/title/{title}", method = RequestMethod.GET)
    @ResponseBody
    public GetProductsResponse getProductByTitle(@PathVariable("title") String title) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.GET_PRODUCTS, "GET", title);
        return productManagementService.getProductByTitle(uuid, title);
    }

    /**
     * make payment
     *
     * @param jsonObject
     * @return
     */
    //@CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject makePayment(@RequestBody JSONObject jsonObject) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.CREATE_ORDER, "POST", jsonObject);
        return orderManagementService.makePayment(uuid, jsonObject);
    }

    /**
     * addProductIntoCart
     *
     * @param productId
     * @param userId
     * @return
     */
   // @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/cart/{productId}/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ECommerceResponse removeProductIntoCart(@PathVariable("productId") String productId, @PathVariable("userId") String userId) {
        String uuid = GenericLogger.getUUID();
        GenericLogger.logRequest(logger, uuid, ECommerceConstants.DELETE_CART, "GET", userId + productId);
        return productManagementService.removeProductIntoCart(uuid, userId, productId );
    }
}
