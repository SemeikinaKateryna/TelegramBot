package com.example.ua_qalight.service;

import com.example.ua_qalight.model.CurrencyJSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri
                (new URI("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json"))
                .GET()
                .build();


        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String res = response.body().toString();
        ObjectMapper mapper = new ObjectMapper();

        List<CurrencyJSON> listJSON = new ArrayList<>();
        TypeReference<List<CurrencyJSON>> listType = new TypeReference<List<CurrencyJSON>>(){};
        listJSON = mapper.readValue(res, listType);

        System.out.println(res);

        System.out.println("===========================");
    }
}
