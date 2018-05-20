use mysql;

select host, user from user;

create user fangp identified by '000000';

grant all on `main-data`.* to fangp@'%' identified by '000000' with grant option;

flush privileges;

-- privileges.sql