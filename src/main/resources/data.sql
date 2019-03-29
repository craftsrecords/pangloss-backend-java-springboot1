insert into users (username, password) values ('admin', '$2a$10$pgkNlYMADhpehGLQjCzTF.pPoykEyRUeilDbBcNsFDI1kBR5gXMOK');
insert into users (username, password) values ('user', '$2a$10$FRLV/jnpo0i3VTMzNypvyOks1lcZ6umRPzOMbpz9mDoB8ufIp2MiG');

insert into authorities (username, authority) values ('admin', 'ADMIN');
insert into authorities (username, authority) values ('user', 'USER');
