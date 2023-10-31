package com.example.ua_qalight.enums;

public enum Emoji {
    SMILE(":smile:"),
    HEART_EYES(":heart_eyes:"),
    BLUSH (":blush:"),
    J(":regional_indicator_symbol_j:"),
    S(":regional_indicator_symbol_s:"),
    O(":regional_indicator_symbol_o:"),
    N(":regional_indicator_symbol_n:"),

    X(":regional_indicator_symbol_x:"),
    M(":regional_indicator_symbol_m:"),
    L(":regional_indicator_symbol_l:");

    private String emoji;

    Emoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
