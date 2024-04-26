package ru.baydak.Models;

import java.util.List;

public class Response{
    private List<Order> orders;

    public Response() {
    }

    public Response(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(List<Order> orders){
        this.orders = orders;
    }

    public List<Order> getOrders(){
        return orders;
    }
}