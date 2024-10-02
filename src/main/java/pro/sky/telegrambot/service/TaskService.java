package pro.sky.telegrambot.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Task;
import pro.sky.telegrambot.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Log4j2
public class TaskService {

    private final TaskRepository taskRepository;

    private static final String REGEXFORINPUTSTRING = "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";
    private static final Pattern PATTERN = Pattern.compile(REGEXFORINPUTSTRING);

    public void save(Long userId, String message) {
        Task task = parseMessage(userId, message);
        if (task != null) {
            log.info("save task success");
            taskRepository.save(task);
        }
    }

    private Task parseMessage(Long userId, String input) {
        Task task = new Task();
        Matcher matcher = PATTERN.matcher(input);
        LocalDateTime date;
        if (matcher.find()) {
            date = LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            String taskMessage = matcher.group(3);
            task.setUserId(userId);
            task.setMessage(taskMessage);
            task.setTimeArrive(date);
            return task;
        }
        return null;
    }


}
