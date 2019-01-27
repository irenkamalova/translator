package com.kamalova.translator;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class MyTranslatorBot extends TelegramLongPollingBot implements Serializable {
    private final String botToken;
    private final String botUserName;
    private final Translator translator;

    public MyTranslatorBot(String botUserName, String botToken, Translator translator) {
        this.botToken = botToken;
        this.botUserName = botUserName;
        this.translator = translator;
    }

    @Override
    public void onUpdateReceived(Update update) {
        onUpdatesReceived(Collections.singletonList(update));
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
            Message updateMessage = update.getMessage();
            if (updateMessage.hasText()) {
                String word = updateMessage.getText();
                Languages language = Languages.getLanguage(word);
                SendMessage sendMessage = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(translator.translate(language, word)
                                + "\n-\nПереведено сервисом «Яндекс.Переводчик» http://translate.yandex.ru/");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBotUsername() {
        return botUserName;
    }

    public String getBotToken() {
        return botToken;
    }
}
