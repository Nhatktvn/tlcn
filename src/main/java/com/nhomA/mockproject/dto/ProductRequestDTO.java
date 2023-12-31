package com.nhomA.mockproject.dto;

public class ProductRequestDTO {
    private Long id;
    private String name;
    private Long category_id;
    private int available;
    private double discount;
    private double price;
    private String urlImage;
    private String description;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, Long category_id, int available, double discount, double price, String urlImage, String description) {
        this.name = name;
        this.category_id = category_id;
        this.available = available;
        this.discount = discount;
        this.price = price;
        this.urlImage = urlImage;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
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
}
