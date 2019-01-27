package com.kamalova.translator;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatHourlyForever;
import static org.quartz.TriggerBuilder.newTrigger;

public class Application {

    public static void main(String[] args) throws SchedulerException, IOException {
        try (InputStream secretIn = Application.class.getClassLoader()
                .getResourceAsStream("secrets.properties");
             InputStream generalIn = Application.class.getClassLoader()
                     .getResourceAsStream("general.properties")) {
            Properties secretProperties = new Properties();
            secretProperties.load(secretIn);
            String botToken = secretProperties.getProperty("bot.token");
            String translatorToken = secretProperties.getProperty("translator.token");

            Properties generalProperties = new Properties();
            generalProperties.load(generalIn);
            String botName = generalProperties.getProperty("bot.name");
            String translatorHost = generalProperties.getProperty("yandex.api.url");

            Translator yaTranslator = new YandexTranslator(translatorHost, translatorToken);

            try {
                MyTranslatorBot translatorBot = botInit(botToken, botName, yaTranslator);
                scheduleBot(translatorBot);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            System.out.println("Registered successfully");
        }


    }

    private static MyTranslatorBot botInit(String botToken, String botName, Translator translator) throws TelegramApiRequestException {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        MyTranslatorBot translatorBot = new MyTranslatorBot(botName, botToken, translator);
        botsApi.registerBot(translatorBot);
        return translatorBot;
    }

    private static void scheduleBot(MyTranslatorBot translatorBot) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();

        JobDetail jobDetail = newJob(TranslatorJob.class).build();
        jobDetail.getJobDataMap().putIfAbsent("translatorBot", translatorBot);

        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(repeatHourlyForever(2))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}