package com.example.ua_qalight.service;

import com.example.ua_qalight.config.BotConfig;
import com.example.ua_qalight.enums.Emoji;
import com.example.ua_qalight.model.CurrencyJSON;
import com.example.ua_qalight.utils.KeyboardRow;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    @Autowired
    public TelegramBot(BotConfig botConfig) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message){
                case "/info":
                    sendMessage(chatId, "This is my first bot on 31.10.2023.");
                    break;
                case "/start":
                    startCommand(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/json":
                    handleRequest(chatId, "Select currency", "JSON");
                    break;
                case "/xml":
                    handleRequest(chatId, "Select currency", "XML");
                    break;
                default:
                    sendMessage(chatId, "Command is unknown.");
            }
        } else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            CurrencyService service = callData.endsWith("JSON") ?
                    new CurrencyJsonService() : new CurrencyXMLService();

            String currency = callData.substring(0,3);

            String message = service.getResponse(currency);

            sendMessage(chatId, message);
            
        }
    }

    private void handleRequest(long chatId, String selectCurrency, String format) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(selectCurrency);

        InlineKeyboardMarkup markup = KeyboardRow.createKeyboard(format);
        message.setReplyMarkup(markup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(long chatId, String sendMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(sendMessage);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void startCommand(long chatId, String firstName) {
        String answer = Emoji.SMILE.getEmoji() + firstName + ", hello." + Emoji.BLUSH.getEmoji()
                + "Nice to meet you"
                + Emoji.HEART_EYES.getEmoji();
        String response = EmojiParser.parseToUnicode(answer);
        sendMessage(chatId, response);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
}
