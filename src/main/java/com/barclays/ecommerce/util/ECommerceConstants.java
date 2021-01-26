package com.barclays.ecommerce.util;

/**
 * ECommerceConstants
 * @author subash
 */
public class ECommerceConstants {

    // Constants for logger
    public final static String LOG_APP_NAME = "Barclays ECommerce";
    public final static String LOG_OPERATION_ID = "[OPERATION ID] : ";
    public final static String LOG_METHOD = "[HTTP METHOD] : ";
    public final static String LOG_REQUEST = "[REQUEST BODY] : ";
    public final static String LOG_RESPONSE = "[RESPONSE BODY] : ";
    public final static String LOG_FAILURE_MSG = "[FAILED TO LOG] : ";
    public final static String LOG_UUID = "[UUID] : ";
    public final static String LOG_STATUS = "[STATUS] : ";
    public final static String LOG_APP = "[APPLICATION] : ";

    // Operation Id
    public final static String CREATE_USER = "createUser";
    public final static String GET_USER = "getUser";
    public static final String GET_LOGGED_IN = "logIn";
    public static final String GET_PRODUCTS = "getProduct";
    public static final String ADD_CART = "addProductInCart";
    public static final String GET_CART = "getProductFromCart";
    public static final String DELETE_CART = "removeProductFromCart";
    public static final String CREATE_ORDER = "createOrder";


    // API response
    public static final String CREATE_RECORD_SUCCESS = "Record created successfully";
    public static final String CREATE_RECORD_FAILURE = "Failed to create record";
    public final static String API_PROCESSED_FAILURE = "Error while processing the API";
    public final static String CART_IS_EMPTY = "Cart is empty";
    public final static String STORING_PRODUCT_FAILURE = "Error while storing product in database";
    public final static String PRODUCT_NOT_AVAILABLE = "Product not available";
    public final static String RECORD_ADD_TO_CART = "Product added into cart successfully";
    public final static String RECORD_ALREADY_EXIST_IN_CART = "Product already available in cart";
    public final static String RECORD_REMOVED_SUCCESS = "Product removed from from cart";
    // API Key & Auth Token
    public final static String X_API_KEY = "test_2259f72c4b9ccb08672815c7346";
    public final static String X_AUTH_TOKEN = "test_21509b690cb9a82a35fb02d8e9b";
    public final static String AUTH_FAILED = "Authentication Failed";
}
