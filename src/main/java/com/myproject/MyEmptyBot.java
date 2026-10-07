package com.myproject;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

public class MyEmptyBot implements LongPollingSingleThreadUpdateConsumer {

    // Клиент, через который мы будем слать ответы в Telegram
    private final TelegramClient telegramClient;

    // Конструктор, принимающий токен (он нужен клиенту)
    public MyEmptyBot(String botToken) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        // Проверяем, что в апдейте есть текстовое сообщение
        if (update.hasMessage() && update.getMessage().hasText()) {
            
            String messageText = update.getMessage().getText(); // Текст от пользователя
            long chatId = update.getMessage().getChatId();       // ID чата, куда слать ответ

            // Переменная для хранения текста ответа
            String replyText;

            // Логика обработки команд
            switch (messageText) {
                case "/author":
                    replyText = "Автор этого бота: [Ваше Имя/Никнейм].";
                    break;
                    
                case "/about":
                    replyText = "Этот бот создан на Java 17 с использованием библиотеки TelegramBots 7.11.0.";
                    break;
                    
                case "/help":
                    replyText = "Доступные команды:\n" +
                                "/author - узнать автора бота\n" +
                                "/about - информация о боте\n" +
                                "/help - показать это меню";
                    break;
                    
                default:
                    replyText = "Я не знаю такой команды. Напишите /help для просмотра списка команд.";
                    break;
            }

            // Отправляем сформированный текст пользователю
            sendNotification(chatId, replyText);
        }
    }

    // Вспомогательный метод для отправки сообщений
    private void sendNotification(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Не удалось отправить сообщение пользователю.");
            e.printStackTrace();
        }
    }
}
