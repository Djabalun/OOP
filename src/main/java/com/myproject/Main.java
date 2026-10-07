package com.myproject;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args) {
        String botToken = System.getenv("BOT_TOKEN"); 

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            
            botsApplication.registerBot(botToken, new MyEmptyBot(botToken));
            
            System.out.println("Бот успешно запущен...");
            
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.out.println("Ошибка при запуске бота.");
            e.printStackTrace();
        }
    }
}
