package com.kamalova.translator;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TranslatorJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            MyTranslatorBot translatorBot = (MyTranslatorBot) jobExecutionContext
                    .getJobDetail().getJobDataMap().get("translatorBot");
            translatorBot.execute(new SendMessage()
                    .setChatId("259411856")
                    .setText("напомниналка"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
