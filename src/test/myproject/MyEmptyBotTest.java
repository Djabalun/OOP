package com.myproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;

public class MyEmptyBotTest {

    private MyEmptyBot bot;
    private FakeTelegramClient fakeClient;

    @BeforeEach
    public void setUp() {
        fakeClient = new FakeTelegramClient();
        // Инициализируем бота тестовым токеном
        bot = new MyEmptyBot("123456:fake_token");
        bot.setTelegramClient(fakeClient); // Передаем фейковый клиент в бота
        
        // Внедряем наш тестовый клиент в бота, чтобы перехватывать ответы
        // (Для этого в классе MyEmptyBot поле telegramClient должно быть доступно или иметь сеттер)
    }

    // ВЕТВЛЕНИЕ 1: Проверка устойчивости к некорректным пробелам
    @Test
    public void testConsume_WithDirtySpaces_ShouldStillExecuteCommand() {
        // Имитируем входящее сообщение с кучей пробелов
        Update update = createMockUpdate("   /about    ");

        bot.consume(update);

        // Проверяем, что бот успешно проигнорировал пробелы и вызвал команду /about
        assertNotNull(fakeClient.getLastSentMessage(), "Бот должен был ответить!");
        String response = fakeClient.getLastSentMessage().getText();
        assertTrue(response.contains("реализует букмекерские услуги"), 
            "Бот должен успешно распознать команду, несмотря на пробелы");
    }

    // ВЕТВЛЕНИЕ 2: Проверка обработки разного регистра букв (/About вместо /about)
    @Test
    public void testConsume_WithDifferentCase_ShouldBeCaseInsensitive() {
        // Имитируем ввод команды заглавными буквами
        Update update = createMockUpdate("/ABOUT");

        bot.consume(update);

        assertNotNull(fakeClient.getLastSentMessage());
        String response = fakeClient.getLastSentMessage().getText();
        // Если в MyEmptyBot внедрен рефакторинг с .toLowerCase(), тест пройдет зелёным. 
        // Если нет — тест упадет (красный), обнаружив ошибку!
        assertTrue(response.contains("реализует букмекерские услуги"));
    }

    // ВЕТВЛЕНИЕ 4: Безопасность при пустых сообщениях (Например, пользователь прислал стикер или фото)
    @Test
    public void testConsume_WithoutText_ShouldNotCrash() {
        Update update = new Update();
        Message message = new Message();
        // Текст НЕ задаем (Сообщение без текста, например, просто картинка)
        update.setMessage(message); 

        // Проверяем, что метод consume() не выкинет NullPointerException, а просто проигнорирует
        assertDoesNotThrow(() -> bot.consume(update), "Бот упал с ошибкой при получении пустого сообщения!");
        assertNull(fakeClient.getLastSentMessage(), "Бот не должен ничего отвечать на пустое сообщение");
    }

    // Вспомогательный метод для быстрой сборки фейкового сообщения от Telegram
    private Update createMockUpdate(String text) {
        Update update = new Update();
        Message message = new Message();
        message.setText(text);
        message.setChat(org.telegram.telegrambots.meta.api.objects.chat.Chat.builder().id(12345L).type("private").build());
        update.setMessage(message);
        return update;
    }
}
