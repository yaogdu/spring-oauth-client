/*
 * Copyright 2013-2015 duolabao.com All right reserved. This software is the
 * confidential and proprietary information of duolabao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with duolabao.com.
 */
package com.andaily.springoauth.service.dto;

/**
 * 类MemberDto的实现描述：TODO 类实现描述
 *
 * @author Administrator 2016/11/17 20:26
 */
public class CustomerDto extends AbstractOauthDto {

    /**
     *
     */
    private static final long serialVersionUID = 2801649559189052215L;

    private String            customerNum;

    private String            customerName;

    private String            address;

    private String            email;

    /**
     * @return the customerNum
     */
    public String getCustomerNum() {
        return customerNum;
    }

    /**
     * @param customerNum the customerNum to set
     */
    public void setCustomerNum(String customerNum) {
        this.customerNum = customerNum;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    private String mobilePhone;
}
