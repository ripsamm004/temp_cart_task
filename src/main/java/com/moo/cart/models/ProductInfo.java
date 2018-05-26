package com.moo.cart.models;

public class ProductInfo {
    private String code;
    private String name;
    private double price;
 
    private boolean newProduct=false;

    public ProductInfo() {
    }
 
    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }
 
    // Không thay đổi Constructor này,
    // nó được sử dụng trong Hibernate query.
    public ProdCartLineInfouctInfo(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    public boolean isNewProduct() {
        return newProduct;
    }
 
    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }
 
}