package com.example.ua_qalight.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardRow {

    public static InlineKeyboardMarkup createKeyboard(String format){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> firstRow = new ArrayList<>();

        InlineKeyboardButton usd = new InlineKeyboardButton();
        usd.setText("USD");
        usd.setCallbackData("USD" + format);

        InlineKeyboardButton eur = new InlineKeyboardButton();
        eur.setText("EUR");
        eur.setCallbackData("EUR" + format);

        InlineKeyboardButton pln = new InlineKeyboardButton();
        pln.setText("PLN");
        pln.setCallbackData("PLN" + format);

        firstRow.add(usd);
        firstRow.add(eur);
        firstRow.add(pln);

        rows.add(firstRow);


        List<InlineKeyboardButton> secondRow = new ArrayList<>();

        InlineKeyboardButton aud = new InlineKeyboardButton();
        aud.setText("AUD");
        aud.setCallbackData("AUD" + format);

        InlineKeyboardButton cad = new InlineKeyboardButton();
        cad.setText("CAD");
        cad.setCallbackData("CAD" + format);

        secondRow.add(aud);
        secondRow.add(cad);

        rows.add(secondRow);


        keyboardMarkup.setKeyboard(rows);

        return keyboardMarkup;
    }
}
