create table users (
  username varchar(256),
  password varchar(256),
  enabled tinyint  default 1 not null
);

create table authorities (
  username varchar(256),
  authority varchar(256)
);