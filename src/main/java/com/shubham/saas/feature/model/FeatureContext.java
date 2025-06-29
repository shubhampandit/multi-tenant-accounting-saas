package com.shubham.saas.feature.model;

import java.util.HashMap;
import java.util.Map;

public class FeatureContext {
    private final Map<String, Object> attributes = new HashMap<>();

    public void put(String key, Object value){
        attributes.put(key, value);
    }

    public <T> T get(String key, Class<T> clazz){
        return clazz.cast(attributes.get(key));
    }

    public boolean contains(String key){
        return attributes.containsKey(key);
    }
}
