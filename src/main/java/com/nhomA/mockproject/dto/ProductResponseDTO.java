package com.nhomA.mockproject.dto;

import com.nhomA.mockproject.entity.User;

import java.time.ZonedDateTime;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private Long category_id;
    private int available;
    private double discount;
    private double price;

    private String urlImage;

    private UserDTO userCreated;
    private UserDTO userUpdated;
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public UserDTO getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(UserDTO userCreated) {
        this.userCreated = userCreated;
    }

    public UserDTO getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(UserDTO userUpdated) {
        this.userUpdated = userUpdated;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
