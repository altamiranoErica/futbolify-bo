package com.tip.futbolifybo.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public interface JSONMapperUtil<T> {

    default T getResultFromJSON(String productJSON, Class<T> tClass){
        T result = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            result = mapper.readValue(productJSON, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
