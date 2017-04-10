package com.project.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 15.02.2017.
 */
@Component
public class StepContainer {

    public final String TITLE = "title";

    private Map<String, Object> storedValues = new HashMap<>();

    public void store(String name, Object value) {
        storedValues.put(name, value);
    }

    public String getValueAsString(String key) {
        return storedValues.get(key).toString();
    }

    public Object getValue(String key) {
        return storedValues.get(key);
    }

}
