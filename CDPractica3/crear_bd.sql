CREATE DATABASE meteochatbd;
USE meteochatbd;

CREATE USER 'adminmeteo'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON meteochatbd. * TO 'adminmeteo'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE clients (
   name VARCHAR(64) NOT NULL,
   email VARCHAR(64) NOT NULL,
   date DATETIME NOT NULL,
   PRIMARY KEY (name)
);

CREATE TABLE friends (
   client1 VARCHAR(64) NOT NULL,
   client2 VARCHAR(64) NOT NULL,
   PRIMARY KEY (client1, client2),
   FOREIGN KEY (client1) REFERENCES clients(name) ON UPDATE CASCADE ON DELETE CASCADE,
   FOREIGN KEY (client2) REFERENCES clients(name) ON UPDATE CASCADE ON DELETE CASCADE
);
