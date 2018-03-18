package com.kamalova.translator;

import com.google.gson.Gson;
import com.kamalova.translator.model.telegram.GetResponse;
import com.kamalova.translator.model.telegram.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslatorApplication {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TranslatorApplication.class);

    /**
     * Все запросы к Telegram Bot API должны осуществляться через HTTPS в следующем виде:
     * https://api.telegram.org/bot<token>/НАЗВАНИЕ_МЕТОДА
     *
     * @param offset
     */

    public GetResponse getUpdate(int offset) throws IOException {
        // https://api.telegram.org/bot584478633:AAE4JVM4Wj8WYpAogsSnCx_36KkwDpN0ym0/getUpdates
        String address = Constants.API_URL + Constants.TOKEN + Constants.GET + "?offset=" + offset;
        URL url = new URL(address);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        LOGGER.debug("Sending 'GET' request to URL : {}", url);
        LOGGER.debug("Response Code: {}", responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        connection.disconnect();
        LOGGER.debug(response.toString());
        return new Gson().fromJson(response.toString(), GetResponse.class);

    }

    public static void main(String[] args) {
        TranslatorApplication t = new TranslatorApplication();
        Translator translator = new YandexTranslator();
        int offset = 0;
        try {
            while (true) {
                GetResponse response = t.getUpdate(offset);
                if (response.getOk() && response.getResult().size() > 0) {
                    List<Result> resultList = response.getResult();
                    for (Result result : resultList) {

                        Integer chat_id = result.getMessage().getChat().getId();
                        String word = result.getMessage().getText();
                        String userName = result.getMessage().getFrom().getUsername();
                        LOGGER.info("USER: {}, MESSAGE: {}", userName, word);
                        String translation;
                        if (word.equals("/start")) {
                            translation = "Привет! Я умею переводить слова с русского на английскийи и с английского на русский." +
                                    " Напиши мне! :)";
                        } else {
                            LANGUAGES language = t.getLanguage(word);
                            translation = translator.translate(language, word);
                        }
                        t.sendMessage(chat_id, translation);
                    }
                    offset = resultList.get(resultList.size() - 1).getUpdate_id() + 1;
                }
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(Integer chat_id, String translation) throws IOException {
        // https://api.telegram.org/bot584478633:AAE4JVM4Wj8WYpAogsSnCx_36KkwDpN0ym0/sendMessage?chat_id=259411856&text=Hello!
        String address = Constants.API_URL + Constants.TOKEN +
                Constants.SEND +
                "?chat_id=" + chat_id +
                "&text=" + translation;
        URL url = new URL(address);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        connection.getResponseCode();
        connection.disconnect();
    }

    private LANGUAGES getLanguage(String word) {
        String pattern = "([a-zA-Z])";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(word);
        return m.find() ? LANGUAGES.EN : LANGUAGES.RU;
    }

}
