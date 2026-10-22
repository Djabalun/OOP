package com.myproject;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class authorscom implements BotCommand {
    @Override
    public String getCommandName() {
        return "/authors";
    }
    @Override
    public String getCommandDescription() { 
        return "Команда /authors показывает имена разработчиков, создавших и поддерживающих этого бота."; 
    }
    @Override
    public void execute(long chatId, TelegramClient telegramClient, String[] args) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Dan & Van")
                .build();
        try {
            telegramClient.execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
