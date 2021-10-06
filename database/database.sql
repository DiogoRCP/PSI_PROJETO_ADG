CREATE DATABASE historycar;
use historycar;

CREATE TABLE IF NOT EXISTS clients(
		id INT UNSIGNED AUTO_INCREMENT,
		clientename VARCHAR(100) NOT NULL,
        nif VARCHAR(9) NOT NULL,
		birthdate DATE NOT NULL,
        mail VARCHAR(100) NOT NULL,
        phonenumber VARCHAR(40) NOT NULL,
        registrationdate DATE NOT NULL,
	CONSTRAINT clients_id PRIMARY KEY(id),
	CONSTRAINT uk_clients_Nif UNIQUE (nif)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cars(
		id INT UNSIGNED AUTO_INCREMENT,
        vin VARCHAR(100) NOT NULL,
        registration VARCHAR(100) NOT NULL,
        purschasedate DATE NOT NULL,
        kilometers INT NOT NULL,
		state VARCHAR(100),
        clientId INT UNSIGNED NOT NULL,
	CONSTRAINT cars_id PRIMARY KEY(id),
    CONSTRAINT uk_cars_vin UNIQUE (vin),
	CONSTRAINT fk_cars_clientId FOREIGN KEY(clientId) REFERENCES clients(id)
) ENGINE=InnoDB;