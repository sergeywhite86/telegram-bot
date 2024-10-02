create table if not exists notification_task
(
    id          bigint primary key,
    userId      bigint,
    message     varchar,
    time_arrive DATE
);