package com.acc.fitnessClubAnalysis.models;

import java.util.List;
import java.util.stream.Collectors;

public class Gym {
    String _name;
    String _url;
    String _address;
    String _phone;
    String _membershipName;
    String _provider;
    String _price;
    Double _effectivePrice;
    List<String> _amenities;

    public Gym() {
    }

    public Gym(String _name,
               String _url,
               String _address,
               String _phone,
               String _membershipName,
               String _provider,
               String _price,
               Double _effectivePrice,
               List<String> _amenities) {
        this._name = _name;
        this._url = _url;
        this._address = _address;
        this._phone = _phone;
        this._membershipName = _membershipName;
        this._provider = _provider;
        this._price = _price;
        this._effectivePrice = _effectivePrice;
        this._amenities = _amenities;
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

    public String get_url() {
        return _url;
    }

    public List<String> get_amenities() {
        return _amenities;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public void set_membershipName(String _membershipName) {
        this._membershipName = _membershipName;
    }

    public void set_provider(String _provider) {
        this._provider = _provider;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public void set_effectivePrice(Double _effectivePrice) {
        this._effectivePrice = _effectivePrice;
    }

    public void set_amenities(List<String> _amenities) {
        this._amenities = _amenities;
    }

    @Override
    public String toString() {
        return "name = " + _name + " , address = " + _address + " , membershipName = " + _membershipName + " , provider = " + _provider + " , price = " + _price
                + " , amenities = [ " + String.join(" ", _amenities) + " ]";
    }
}
