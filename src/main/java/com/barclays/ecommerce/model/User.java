package com.barclays.ecommerce.model;

import org.springframework.data.annotation.Id;

/**
 * UserModel
 *
 * @author subash
 */
public class User {

    @Id
    private String userId;
    private String name;
    private String emailId;
    private String phoneNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
