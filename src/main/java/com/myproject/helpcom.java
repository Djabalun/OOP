package com.myproject;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import java.util.Map;

public class helpcom implements BotCommand {
    
    private final Map<String, BotCommand> allCommands;

    public helpcom(Map<String, BotCommand> allCommands) {
        this.allCommands = allCommands;
    }

    @Override
    public String getCommandName() { return "/help"; }

    @Override
    public String getCommandDescription() {
        return "Команда /help выводит список всех функций.\nИспользуйте '/help [команда]' для подробной справки (например, /help /about).";
    }

    @Override
    public void execute(long chatId, TelegramClient telegramClient, String[] args) {
        String replyText;

        if (args.length > 0) {
            String targetCommand = args[0];
            
            BotCommand cmd = allCommands.get(targetCommand);
            
            if (cmd != null) {
                replyText = "Справка по команде " + targetCommand + ":\n" + cmd.getCommandDescription();
            } else {
                replyText = "Команда " + targetCommand + " не найдена. Воспользуйтесь командой /help.";
            }
        } else {
            StringBuilder sb = new StringBuilder("Доступные команды:\n");
            for (BotCommand cmd : allCommands.values()) {
                sb.append(cmd.getCommandName()).append(" - подробнее через /help ").append(cmd.getCommandName()).append("\n");
            }
            replyText = sb.toString();
        }

        SendMessage message = SendMessage.builder().chatId(chatId).text(replyText).build();
        try { telegramClient.execute(message); } catch (Exception e) { e.printStackTrace(); }
    }
}
