package com.acc.fitnessClubAnalysis.models;

public class Gym {
    String _name;
    String _address;
    String _phone;
    String _membershipName;
    String _provider;
    String _price;
    Double _effectivePrice;

    public Gym(String _name,
               String _address,
               String _phone,
               String _membershipName,
               String _provider,
               String _price,
               Double _effectivePrice) {
        this._name = _name;
        this._address = _address;
        this._phone = _phone;
        this._membershipName = _membershipName;
        this._provider = _provider;
        this._price = _price;
        this._effectivePrice = _effectivePrice;
    }


    public String get_name() {
        return _name;
    }

    public String get_address() {
        return _address;
    }

    public String get_phone() {
        return _phone;
    }

    public String get_membershipName() {
        return _membershipName;
    }

    public String get_provider() {
        return _provider;
    }

    public String get_price() {
        return _price;
    }

    public Double get_effectivePrice() {
        return _effectivePrice;
    }


    @Override
    public String toString() {
        return "name = " + _name + " , address = " + _address + " , membershipName = " + _membershipName + " , provider = " + _provider + " , price = " + _price;
    }
}
