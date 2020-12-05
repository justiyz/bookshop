drop user if exists 'bookuser'@'localhost';
create user 'bookuser'@'localhost' identified by 'bookuser123';
grant all privileges on bookstoredb.* to 'bookuser'@'localhost';
flush privileges;

drop database if exists bookstoredb;
create database bookstoredb;