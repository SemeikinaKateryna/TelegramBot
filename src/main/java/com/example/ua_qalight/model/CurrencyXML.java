package com.example.ua_qalight.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyXML {
    @JsonProperty("cc")
    private String currency;

    @JsonProperty("rate")
    private double rate;
}
