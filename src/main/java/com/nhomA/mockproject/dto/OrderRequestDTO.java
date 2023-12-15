package com.nhomA.mockproject.dto;

import java.time.LocalDate;

public class OrderRequestDTO {
    private Long addressId;
    private String name;
    private String phone;

    @Override
    public String toString() {
        return "{" +
                "addressId=" + addressId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
