package com.myproject;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

import java.util.HashMap;
import java.util.Map;

public class MyEmptyBot implements LongPollingSingleThreadUpdateConsumer {

	private final TelegramClient telegramClient;

	private final Map<String, BotCommand> commands = new HashMap<>();

	public MyEmptyBot(String botToken) {
		this.telegramClient = new OkHttpTelegramClient(botToken);

		commands.put("/authors", new authorscom());
		commands.put("/about", new aboutcom());
		commands.put("/help", new helpcom(commands));
	}

	@Override
	public void consume(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText().trim();
			long chatId = update.getMessage().getChatId();

			String[] parts = messageText.split("\\s+");
			String commandName = parts[0];

			String[] args = new String[parts.length - 1];
			System.arraycopy(parts, 1, args, 0, args.length);

			BotCommand command = commands.get(commandName);

			if (command != null) {
				command.execute(chatId, telegramClient, args);
			} else {
				sendNotification(chatId,
						"Неизвестная команда. Воспользуйтесь командой '/help', чтобы узнать возможности бота");
			}
		}
	}

	private void sendNotification(long chatId, String text) {
		SendMessage message = SendMessage.builder().chatId(chatId).text(text).build();
		try {
			telegramClient.execute(message);
		} catch (TelegramApiException e) {
			System.out.println("Не удалось отправить сообщение пользователю.");
			e.printStackTrace();
		}
	}
	
	// Добавьте этот метод в MyEmptyBot.java для связи с автотестами
	public void setTelegramClient(org.telegram.telegrambots.meta.generics.TelegramClient telegramClient) {
	    // Убираем final у поля telegramClient, если оно там стоит, и перезаписываем
	    this.telegramClient = telegramClient;
	}

}
