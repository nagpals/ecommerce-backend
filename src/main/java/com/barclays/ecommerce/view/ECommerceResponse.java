package com.barclays.ecommerce.view;

/**
 * ECommerceResponse
 *
 * @author subashchandar s
 */
public class ECommerceResponse {

    private boolean isSuccess;
    private String responseMessage;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
