package com.myproject;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args) {
        String botToken = bot.token; 

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            
            botsApplication.registerBot(botToken, new MyEmptyBot(botToken));
            
            System.out.println("Чистый бот успешно запущен и работает в фоне...");
            
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.out.println("Ошибка при запуске бота. Проверьте токен или интернет.");
            e.printStackTrace();
        }
    }
}
