package com.myproject;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

public class MyEmptyBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;

    public MyEmptyBot(String botToken) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            
            String messageText = update.getMessage().getText(); // Текст от пользователя
            long chatId = update.getMessage().getChatId();       // ID чата, куда слать ответ

            String replyText;

            switch (messageText) {
                case "/authors":
                    replyText = "Dan & Van";
                    break;
                    
                case "/about":
                    replyText = "Этот бот создан для конвертации файлов.";
                    break;
                    
                case "/help":
                    replyText = "Доступные команды:\n" +
                                "/authors - узнать авторов бота\n" +
                                "/about - назначение бота\n" +
                                "/help - показать это меню";
                    break;
                    
                default:
                    replyText = "Неизвестная команда. Воспользуйтесь командой '/help', чтобы узнать возможности бота";
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
