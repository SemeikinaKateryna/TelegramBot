package com.example.ua_qalight.service;

import com.example.ua_qalight.enums.Emoji;
import com.example.ua_qalight.model.CurrencyXML;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
public class CurrencyXMLService implements CurrencyService{
    private static String URL;

    private static List<CurrencyXML> currencyXMLList = new ArrayList<>();

    @Value("${xml.url}")
    public void setURL(String url){
        CurrencyXMLService.URL = url;
    }

    @Override
    @SneakyThrows
    public String getResponse(String currency) {
        if (currencyXMLList.isEmpty()) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .GET()
                    .build();


            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String res = response.body().toString();
            XmlMapper mapper = new XmlMapper();

            TypeReference<List<CurrencyXML>> listType = new TypeReference<List<CurrencyXML>>() {};
            currencyXMLList = mapper.readValue(res, listType);
        }

        CurrencyXML curr = getCurrencyXML(currency);

         String result = curr != null ?
                curr.getCurrency() + " rate is " + curr.getRate() + " - "
                        + Emoji.X.getEmoji() + " " + Emoji.M.getEmoji() + " " + Emoji.L.getEmoji()
                : "ERROR!";

        return EmojiParser.parseToUnicode(result);
    }

    private CurrencyXML getCurrencyXML(String currency) {
        CurrencyXML currencyXML = currencyXMLList
                .stream()
                .filter(el->el.getCurrency().equals(currency))
                .findAny()
                .orElse(null);
        return currencyXML;

    }


}
