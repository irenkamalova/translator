package com.kamalova.translator;

public enum LANGUAGES {
    RU("ru-en"),
    EN("en-ru");

    private final String language;

    LANGUAGES(String s) {
        language = s;
    }

    String getLanguage() {
        return language;
    }
}
