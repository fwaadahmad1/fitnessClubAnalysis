package com.acc.fitnessClubAnalysis.models;

public class Gym {
    String name;
    String address;
    String membershipName;
    String provider;
    String price;
    Double effectivePrice;

    public Gym(String name,
               String address,
               String membershipName,
               String provider,
               String price,
               Double effectivePrice) {
        this.name = name;
        this.address = address;
        this.membershipName = membershipName;
        this.provider = provider;
        this.price = price;
        this.effectivePrice = effectivePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getEffectivePrice() {
        return effectivePrice;
    }

    public void setEffectivePrice(Double effectivePrice) {
        this.effectivePrice = effectivePrice;
    }

    @Override
    public String toString() {
        return "name = " + name + " , address = " + address + " , membershipName = " + membershipName + " , provider = " + provider + " , price = " + price;
    }
}
