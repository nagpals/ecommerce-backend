package com.barclays.ecommerce.service;

import com.barclays.ecommerce.model.Order;
import com.barclays.ecommerce.model.Product;
import com.barclays.ecommerce.util.ECommerceConstants;
import com.barclays.ecommerce.util.GenericLogger;
import com.barclays.ecommerce.view.ECommerceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
@Component
public class OrderManagementService {

    private static final Logger logger = LogManager.getLogger(OrderManagementService.class);

    /**
     * @param uuid
     * @param jsonObject
     * @return
     */
    public JSONObject makePayment(String uuid, JSONObject jsonObject) {
        JSONObject response = new JSONObject();
        try {
            String purpose = (String) jsonObject.get("purpose");
            String amount = (String) jsonObject.get("amount");
            String address = (String) jsonObject.get("address");
            String userName = (String) jsonObject.get("userName");
            String emailId = (String) jsonObject.get("emailId");
            String phoneNo =  (String) jsonObject.get("phoneNo");

            final String uri = "http://www.instamojo.com/api/1.1/payment-requests";
            RestTemplate restTemplate = new RestTemplate();
            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            headers.add("X-Api-Key", ECommerceConstants.X_API_KEY);
            headers.add("X-Auth-Token", ECommerceConstants.X_AUTH_TOKEN);

            // create a map for post parameters
            Map<String, Object> map = new HashMap<>();
            map.put("purpose", purpose);
            map.put("amount", Double.valueOf(amount));
            map.put("buyer_name", userName);
            map.put("email", emailId);
            map.put("phone", phoneNo);
            map.put("send_email", false);

            // build the request
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

            // send POST request
            ResponseEntity<String> httpResponse = restTemplate.postForEntity(uri, entity, String.class);
            if(httpResponse.getStatusCode().is2xxSuccessful()) {
                JSONObject httpJsonObject = new ObjectMapper().readValue(httpResponse.getBody(), JSONObject.class);
                String paymentTransId = (String) httpJsonObject.get("id");
                response.put("isSuccess", true);
                response.put("id",paymentTransId);
                response.put("status", httpJsonObject.get("status"));
                response.put("phone", httpJsonObject.get("phone"));
                response.put("email", httpJsonObject.get("email"));
                response.put("buyer_name", httpJsonObject.get("buyer_name"));
                response.put("amount", httpJsonObject.get("amount"));
                response.put("purpose", httpJsonObject.get("purpose"));
                GenericLogger.logResponse(logger, uuid, "SUCCESS", httpResponse.getBody());
                // Store into order table
                // updateOrder()
            } else if(httpResponse.getStatusCode().is4xxClientError()){
                response.put("isSuccess", false);
                response.put("responseMessage",ECommerceConstants.AUTH_FAILED);
                GenericLogger.logResponse(logger, uuid, "SUCCESS", httpResponse.getBody());
            } else {
                response.put("isSuccess", false);
                response.put("responseMessage",ECommerceConstants.API_PROCESSED_FAILURE + " : " + httpResponse.getBody());
            }
        } catch (Exception e) {
            response.put("isSuccess", false);
            response.put("responseMessage",ECommerceConstants.API_PROCESSED_FAILURE);
            // Logger error response
            GenericLogger.logResponse(logger, uuid, "ERROR", ECommerceConstants.API_PROCESSED_FAILURE);
            logger.info(ECommerceConstants.API_PROCESSED_FAILURE + " : " + e.getMessage());
        }
        return response;
    }
}
