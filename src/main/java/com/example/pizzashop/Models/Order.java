package com.example.pizzashop.Models;

public class Order {
    private String pizzaName;
    private String castomPizzaName;
    private String phone;
    private String address;


    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCastomPizzaName() {
        return castomPizzaName;
    }

    public void setCastomPizzaName(String castomPizzaName) {
        this.castomPizzaName = castomPizzaName;
    }
}
