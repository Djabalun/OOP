package com.myproject;

import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface BotCommand {
    String getCommandName();
    String getCommandDescription();
    void execute(long chatId, TelegramClient telegramClient, String[] args);
}
