package com.kamalova.translator;

import com.google.gson.Gson;
import com.kamalova.translator.model.yandex.YandexTranslatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class YandexTranslator implements Translator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TranslatorApplication.class);


    public String translate(LANGUAGES language, String word) {
        try {
            return doRequest(language, word);
        } catch (IOException e) {
            e.printStackTrace();
            return "Service is not available :(\n" + e.getMessage();
        }
    }
//        https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180317T194431Z.fc18b10f64b5a086.9f355f87b7860c1b5248682a95303611cdf95f3e&lang=en-ru&text=word


    private String doRequest(LANGUAGES language, String word) throws IOException {
        /*
        https://translate.yandex.net/api/v1.5/tr.json/translate
        ? [key=<API-ключ>]
        & [text=<переводимый текст>]
        & [lang=<направление перевода>]
        & [format=<формат текста>]
        & [options=<опции перевода>]
        & [callback=<имя callback-функции>]
        */
        String address = Constants.YANDEX_URL +
                "?key=" + Constants.YANDEX_KEY +
                "&lang=" + language.getLanguage();


        URL url = new URL(address);

        byte[] data = ("text=" + word).getBytes("UTF-8");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(data.length));
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(data);

        int responseCode = connection.getResponseCode();
        LOGGER.info("Sending 'POST' request to URL : " + url);
        LOGGER.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        LOGGER.info(response.toString());

        connection.disconnect();

        return new Gson().fromJson(response.toString(), YandexTranslatorResponse.class).getText().get(0);

    }
}
