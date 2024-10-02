package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.TaskService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TaskService taskService;

    private final TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            String input = update.message().text();
            if (input.equals("/start")) {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Введите заметку в формате:01.01.2022 20:00 Сделать домашнюю работу"));
            } else taskService.save(update.message().chat().id(), input);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
