package com.myproject;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class aboutcom implements BotCommand {
	@Override
	public String getCommandName() {
		return "/about";
	}

	@Override
	public String getCommandDescription() {
		return "Команда /about показывает назначение бота, а также некоторые другие сведения о его работе и взаимодействии с пользователями.";
	}

	@Override
	public void execute(long chatId, TelegramClient telegramClient, String[] args) {
		SendMessage message = SendMessage.builder().chatId(chatId)
				.text("Этот бот является учебным и реализует букмекерские услуги.\n"
						+ "Внимание! Данный бот не использует никаких личных данных"
						+ "пользователей. Услуги, предоставляемые ботом, яявляются абсолютно бесплатными и не требуют "
						+ "от пользователей какой либо оплаты.")
				.build();
		try {
			telegramClient.execute(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
