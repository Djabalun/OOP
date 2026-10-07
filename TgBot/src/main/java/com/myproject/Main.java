package com.myproject;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args) {

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            
            // Запускаем нашего абсолютно пустого бота
            botsApplication.registerBot(Config.BOT_TOKEN, new MyEmptyBot());
            
            System.out.println("Чистый бот успешно запущен и работает в фоне...");
            
            // Эта строчка не дает программе сразу закрыться
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.out.println("Ошибка при запуске бота. Проверьте токен или интернет.");
            e.printStackTrace();
        }
    }
}
