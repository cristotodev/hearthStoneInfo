CREATE USER 'anonimo'@'localhost' IDENTIFIED BY 'anonimo';

grant delete on dbhearthstoneinfo . * to 'anonimo'@'localhost';
grant insert on dbhearthstoneinfo . * to 'anonimo'@'localhost';
grant update on dbhearthstoneinfo . * to 'anonimo'@'localhost';
grant select on dbhearthstoneinfo . * to 'anonimo'@'localhost';

flush privileges;