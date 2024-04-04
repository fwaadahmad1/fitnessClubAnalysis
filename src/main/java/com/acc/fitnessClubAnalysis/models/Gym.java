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

    public String getAddress() {
        return address;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public String getProvider() {
        return provider;
    }

    public String getPrice() {
        return price;
    }

    public Double getEffectivePrice() {
        return effectivePrice;
    }


    @Override
    public String toString() {
        return "name = " + name + " , address = " + address + " , membershipName = " + membershipName + " , provider = " + provider + " , price = " + price;
    }
}
