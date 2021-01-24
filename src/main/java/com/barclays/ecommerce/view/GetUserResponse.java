package com.barclays.ecommerce.view;

/**
 * GetUserResponse
 * @author subash
 */
public class GetUserResponse extends UserView{

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
