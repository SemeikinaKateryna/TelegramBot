package com.example.ua_qalight.service;

import com.example.ua_qalight.enums.Emoji;
import com.example.ua_qalight.model.CurrencyJSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("application.properties")
public class CurrencyJsonService implements CurrencyService{

    private static String URL;

    private static List<CurrencyJSON> currencyJSONList = new ArrayList<>();

    @Value("${json.url}")
    public void setURL(String url){
        CurrencyJsonService.URL = url;
    }

    @Override
    @SneakyThrows
    public String getResponse(String currency) {
        if (currencyJSONList.isEmpty()) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .GET()
                    .build();


            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String res = response.body().toString();
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<List<CurrencyJSON>> listType = new TypeReference<List<CurrencyJSON>>() {};
            currencyJSONList = mapper.readValue(res, listType);
        }
        CurrencyJSON curr = getCurrencyJson(currency);

        String result = curr != null ?
                curr.getCurrency() + " rate is " + curr.getRate() + " - " + Emoji.J.getEmoji() + " "
                        + Emoji.S.getEmoji() + " " + Emoji.O.getEmoji() + " " + Emoji.N.getEmoji()
                : "ERROR!";
        return EmojiParser.parseToUnicode(result);
    }

    private static CurrencyJSON getCurrencyJson(String currency){
        CurrencyJSON currencyJSON = currencyJSONList
                .stream()
                .filter(el->el.getCurrency().equals(currency))
                .findAny()
                .orElse(null);
        return currencyJSON;
    }
}
