create table users (
  username varchar(256),
  password varchar(256),
  enabled tinyint  default 1 not null
);