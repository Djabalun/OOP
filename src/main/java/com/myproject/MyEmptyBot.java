package com.myproject;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

// Этот класс — просто "слушатель" Telegram. Он пока ничего не делает.
public class MyEmptyBot implements LongPollingSingleThreadUpdateConsumer {

    @Override
    public void consume(Update update) {
        // Здесь абсолютно пусто. Бот получает сообщения от серверов Telegram,
        // но никак на них не реагирует. Чистый лист для вашей будущей логики.
    }
}
