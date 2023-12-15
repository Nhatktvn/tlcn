package com.nhomA.mockproject.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @Column(name = "delivery_time")
    private ZonedDateTime deliveryTime;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne
    @JoinColumn(name = "vnPayInfo_id", referencedColumnName = "id")
    private VnPayInfo vnPayInfo;

    @Column(name = "txnRef_VnPay", unique = true)
    private String txnRefVnPay;
    @OneToMany(mappedBy = "order",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    private List<CartLineItem> cartLineItems;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_order", referencedColumnName = "id")
    private StatusOrder statusOrder;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public String getTxnRefVnPay() {
        return txnRefVnPay;
    }

    public void setTxnRefVnPay(String txnRefVnPay) {
        this.txnRefVnPay = txnRefVnPay;
    }

    public VnPayInfo getVnPayInfo() {
        return vnPayInfo;
    }

    public void setVnPayInfo(VnPayInfo vnPayInfo) {
        this.vnPayInfo = vnPayInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public ZonedDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(ZonedDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartLineItem> getCartLineItems() {
        return cartLineItems;
    }

    public void setCartLineItems(List<CartLineItem> cartLineItems) {
        this.cartLineItems = cartLineItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
