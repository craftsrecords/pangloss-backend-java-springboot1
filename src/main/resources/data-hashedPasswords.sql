insert into users (username, password) values ('admin', '$2a$10$MpmSeH.Eby7gtG5x5wD81uHXeN1M/e7R3fKZcnABcX4Es4MvvFasm');
insert into users (username, password) values ('user', '$2a$10$ZHDJXCUtNePyjC3jnyFQzeNUaMS93dXbsJj7FH9n27IkvAWQonsbe');

insert into authorities (username, authority) values ('admin', 'ADMIN');
insert into authorities (username, authority) values ('user', 'USER');
