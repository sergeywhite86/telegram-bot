package pro.sky.telegrambot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "notification_task")
@NoArgsConstructor
@Setter
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private long UserId;

    private String message;

    LocalDateTime timeArrive;

}
