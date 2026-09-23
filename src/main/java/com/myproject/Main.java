package com.myproject;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args) {
        // ТОКЕН: получите его у @BotFather в Telegram и вставьте сюда вместо кавычек
        String botToken = "8905210841:AAHpxy_bQSZ9koTzgYkKG45kCFmmDMd--WY"; 

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            
            // Запускаем нашего абсолютно пустого бота
            botsApplication.registerBot(botToken, new MyEmptyBot());
            
            System.out.println("Чистый бот успешно запущен и работает в фоне...");
            
            // Эта строчка не дает программе сразу закрыться
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.out.println("Ошибка при запуске бота. Проверьте токен или интернет.");
            e.printStackTrace();
        }
    }
}
