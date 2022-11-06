package com.mdsujan.restProges.response;

public class ItemResponse {
    private String id;
    private String desc;
    private String category;
    private String type;
    private String status;
    private Double price;
    private Boolean pickupAllowed;
    private Boolean shippingAllowed;
    private Boolean deliveryAllowed;

    public ItemResponse(String id, String desc, String category, String type, String status, Double price,
                        Boolean pickupAllowed, Boolean shippingAllowed, Boolean deliveryAllowed) {
        this.id = id;
        this.desc = desc;
        this.category = category;
        this.type = type;
        this.status = status;
        this.price = price;
        this.pickupAllowed = pickupAllowed;
        this.shippingAllowed = shippingAllowed;
        this.deliveryAllowed = deliveryAllowed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getPickupAllowed() {
        return pickupAllowed;
    }

    public void setPickupAllowed(Boolean pickupAllowed) {
        this.pickupAllowed = pickupAllowed;
    }

    public Boolean getShippingAllowed() {
        return shippingAllowed;
    }

    public void setShippingAllowed(Boolean shippingAllowed) {
        this.shippingAllowed = shippingAllowed;
    }

    public Boolean getDeliveryAllowed() {
        return deliveryAllowed;
    }

    public void setDeliveryAllowed(Boolean deliveryAllowed) {
        this.deliveryAllowed = deliveryAllowed;
    }

    @Override
    public String toString() {
        return "ItemResponse{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", pickupAllowed=" + pickupAllowed +
                ", shippingAllowed=" + shippingAllowed +
                ", deliveryAllowed=" + deliveryAllowed +
                '}';
    }
}
