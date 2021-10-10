CREATE DATABASE carbuddy;
use carbuddy;

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

CREATE TABLE IF NOT EXISTS companies(
		id INT UNSIGNED AUTO_INCREMENT,
		companyname VARCHAR(100) NOT NULL,
        nif VARCHAR(9) NOT NULL,
        mail VARCHAR(100) NOT NULL,
        phonenumber VARCHAR(40) NOT NULL,
        registrationdate DATE NOT NULL,
	CONSTRAINT companies_id PRIMARY KEY(id),
	CONSTRAINT uk_companies_Nif UNIQUE (nif)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS contributors(
		id INT UNSIGNED AUTO_INCREMENT,
		contributorname VARCHAR(100) NOT NULL,
        nif VARCHAR(9) NOT NULL,
		birthdate DATE NOT NULL,
        mail VARCHAR(100) NOT NULL,
        phonenumber VARCHAR(40) NOT NULL,
        registrationdate DATE NOT NULL,
        specialty VARCHAR(100) NOT NULL,
        companyId INT UNSIGNED NOT NULL,
	CONSTRAINT contributors_id PRIMARY KEY(id),
	CONSTRAINT uk_contributors_Nif UNIQUE (nif),
    CONSTRAINT fk_contributors_companyId FOREIGN KEY(companyId) REFERENCES companies(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cars(
		id INT UNSIGNED AUTO_INCREMENT,
        vin VARCHAR(100) NOT NULL,
        registration VARCHAR(100) NOT NULL,
        purschasedate DATE NOT NULL,
        kilometers INT NOT NULL,
		state VARCHAR(100) NOT NULL,
        clientId INT UNSIGNED NOT NULL,
	CONSTRAINT cars_id PRIMARY KEY(id),
    CONSTRAINT uk_cars_vin UNIQUE (vin),
	CONSTRAINT fk_cars_clientId FOREIGN KEY(clientId) REFERENCES clients(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS repairs(
		id INT UNSIGNED AUTO_INCREMENT,
		kilometers INT NOT NULL,
        repairdate DATE NOT NULL,
        repairdescription VARCHAR(100) NOT NULL,
        state VARCHAR(100) NOT NULL,
        carId INT UNSIGNED NOT NULL,
        contributorId INT UNSIGNED NOT NULL,
	CONSTRAINT repairs_id PRIMARY KEY(id),
	CONSTRAINT fk_repairs_carId FOREIGN KEY(carId) REFERENCES cars(id),
    CONSTRAINT fk_repairs_contributorId FOREIGN KEY(contributorId) REFERENCES contributors(id)
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS maintenance(
		id INT UNSIGNED AUTO_INCREMENT,
		kilometers INT NOT NULL,
        maintenancedate DATE NOT NULL,
        maintenancedescription VARCHAR(100) NOT NULL,
        state VARCHAR(100) NOT NULL,
        carId INT UNSIGNED NOT NULL,
        contributorId INT UNSIGNED NOT NULL,
	CONSTRAINT repairs_id PRIMARY KEY(id),
	CONSTRAINT fk_maintenance_carId FOREIGN KEY(carId) REFERENCES cars(id),
    CONSTRAINT fk_maintenance_contributorId FOREIGN KEY(contributorId) REFERENCES contributors(id)
) ENGINE=InnoDB;