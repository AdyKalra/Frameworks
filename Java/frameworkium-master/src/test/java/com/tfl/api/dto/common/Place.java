package com.tfl.api.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    public String commonName;
    public String lat;
    public String lon;
}
