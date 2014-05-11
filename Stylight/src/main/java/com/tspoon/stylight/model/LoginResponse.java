package com.tspoon.stylight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

    @JsonProperty("loginstatus")
    private String status;

    @JsonProperty("sessionId")
    private String sessionId;
}
