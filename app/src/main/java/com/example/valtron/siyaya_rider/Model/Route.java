package com.example.valtron.siyaya_rider.Model;

public class Route {
    private String Route_name;
    private String Time;
    private String Price;

    public Route(String route_name, String time, String price) {
        Route_name = route_name;
        Time = time;
        Price = price;
    }

    public String getRoute_name() {
        return Route_name;
    }

    public void setRoute_name(String route_name) {
        Route_name = route_name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
