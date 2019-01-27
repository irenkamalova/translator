package com.kamalova.translator;

import com.google.gson.Gson;
import com.kamalova.translator.yandex.YandexTranslatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class YandexTranslator implements Translator {
    private static final Logger logger = LoggerFactory.getLogger(YandexTranslator.class);

    private final String host;
    private final String yandexToken;

    public YandexTranslator(String host, String yandexToken) {
        this.host = host;
        this.yandexToken = yandexToken;
    }


    public String translate(Languages language, String word) {
        try {
            return doRequest(language, word);
        } catch (IOException e) {
            e.printStackTrace();
            return "Service is not available :(\n" + e.getMessage();
        }
    }

    private String doRequest(Languages language, String word) throws IOException {
        /*
        https://translate.yandex.net/api/v1.5/tr.json/translate
        ? [key=<API-ключ>]
        & [text=<переводимый текст>]
        & [lang=<направление перевода>]
        & [format=<формат текста>]
        & [options=<опции перевода>]
        & [callback=<имя callback-функции>]
        */
        String address = host +
                "?key=" + yandexToken +
                "&lang=" + language.getLanguage();


        URL url = new URL(address);
        byte[] data = ("text=" + word).getBytes(StandardCharsets.UTF_8);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(data.length));
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(data);

        int responseCode = connection.getResponseCode();
        logger.info("Word " + word + " translated by response " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return new Gson().fromJson(response.toString(), YandexTranslatorResponse.class).getText().get(0);

    }
}
