package com.example.valtron.siyaya_rider.Model;

public class Rider {
    private String name, phone, avatarUrl, route, Reg, Status;

    public Rider() {
    }

    public Rider(String name, String phone, String avatarUrl, String route, String reg, String status) {
        this.name = name;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.route = route;
        Reg = reg;
        Status = status;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getReg() {
        return Reg;
    }

    public void setReg(String reg) {
        Reg = reg;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
