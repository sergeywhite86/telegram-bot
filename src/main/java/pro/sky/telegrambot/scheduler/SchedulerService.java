package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Task;
import pro.sky.telegrambot.repository.TaskRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
@Log4j2
public class SchedulerService {

  private final TaskRepository repository;

  private final TelegramBot bot;

  @Scheduled(cron = "${interval-in-cron}")
  public void scheduleSendTask() {
    log.info("Scheduling tasks");
    getTasks().forEach(task -> bot.execute(new SendMessage(task.getUserId(),task.getMessage())));
  }

  @Scheduled(cron = "59 59 23 * * *")
  @Transactional
  public void scheduleCleanTask() {
    log.info("Scheduling clean tasks");
    repository.deleteByTimeArriveBefore(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
  }

  private List<Task> getTasks(){
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    return repository.findByTimeArrive(now);
  }
}
