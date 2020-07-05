package com.tmorris.reunitedtrav.models.converter;

import com.google.gson.Gson;
import com.tmorris.reunitedtrav.models.Address;

import javax.persistence.AttributeConverter;

public class AddressConverter implements AttributeConverter<Address, String> {
    private final Gson gson;

    public AddressConverter() {
        gson = new Gson();
    }

    @Override
    public String convertToDatabaseColumn(Address address) {
        String json;
        try {
            json = gson.toJson(address);
        } catch (Exception ex) {
            ex.printStackTrace();
            json = "";
        }
        return json;
    }

    @Override
    public Address convertToEntityAttribute(String addressString) {
        Address jsonData;
        try {
            jsonData = gson.fromJson(addressString, Address.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            jsonData = null;
        }
        return jsonData;
    }
}
