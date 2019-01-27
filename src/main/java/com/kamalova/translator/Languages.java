package com.kamalova.translator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Languages {
    RU("ru-en"),
    EN("en-ru");

    private final String language;

    Languages(String s) {
        language = s;
    }

    String getLanguage() {
        return language;
    }

    public static Languages getLanguage(String word) {
        String pattern = "([a-zA-Z])";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(word);
        return m.find() ? Languages.EN : Languages.RU;
    }
}
