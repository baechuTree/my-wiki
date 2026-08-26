CREATE DATABASE my_wiki_for_test default CHARACTER SET UTF8MB4;

SHOW DATABASES;

DROP DATABASE my_wiki_for_test;

GRANT ALL PRIVILEGES ON my_wiki_for_test.* TO 'wiki_user'@'localhost';