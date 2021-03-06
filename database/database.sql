CREATE DATABASE carbuddy;
use carbuddy;


CREATE TABLE IF NOT EXISTS companies(
		id INT UNSIGNED AUTO_INCREMENT,
		companyname VARCHAR(100) NOT NULL,
        nif VARCHAR(9) NOT NULL,
        email VARCHAR(100) NOT NULL,
        phonenumber VARCHAR(40) NOT NULL,
        registrationdate DATETIME default Current_Timestamp,
	CONSTRAINT companies_id PRIMARY KEY(id),
	CONSTRAINT uk_companies_Nif UNIQUE (nif)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user(
		id INT UNSIGNED AUTO_INCREMENT,
		username VARCHAR(255) NOT NULL,
        password_hash varchar(255) NOT NULL,
        verification_token TEXT NOT NULL,
        password_reset_token varchar(255),
        auth_key varchar(32) NOT NULL,
        status smallint(6) NOT NULL default 10,
        updated_at INT NOT NULL,
        created_at INT NOT NULL,
        usertype VARCHAR(100) NOT NULL,
        nif VARCHAR(9) NOT NULL,
		birsthday DATE NOT NULL,
        email VARCHAR(255) NOT NULL,
        phonenumber VARCHAR(40) NOT NULL,
        registrationdate DATETIME default Current_Timestamp,
	CONSTRAINT users_id PRIMARY KEY(id),
	CONSTRAINT uk_users_Nif UNIQUE (nif),
    CONSTRAINT uk_users_user UNIQUE (username),
	CONSTRAINT uk_users_passwordresettoken UNIQUE (password_reset_token),
    CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS contributors(
		id INT UNSIGNED AUTO_INCREMENT,
        speciality VARCHAR(100) NOT NULL,
        companyId INT UNSIGNED NOT NULL,
        userId INT UNSIGNED NOT NULL,
	CONSTRAINT contributors_id PRIMARY KEY(id),
    CONSTRAINT fk_contributors_companyId FOREIGN KEY(companyId) REFERENCES companies(id),
	CONSTRAINT fk_contributors_userId FOREIGN KEY(userId) REFERENCES user(id),
    CONSTRAINT uk_contributors_userId UNIQUE (userId)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS cars(
		id INT UNSIGNED AUTO_INCREMENT,
        vin VARCHAR(100) NOT NULL,
        brand VARCHAR(100) NOT NULL,
        model VARCHAR(100) NOT NULL,
        color VARCHAR(100) NOT NULL,
        carType VARCHAR(100) NOT NULL,
        displacement FLOAT NOT NULL,
        fuelType VARCHAR(100) NOT NULL,
        registration VARCHAR(100) NOT NULL,
        modelyear year(4) NOT NULL,
        kilometers INT NOT NULL,
		state VARCHAR(100) NOT NULL,
        userId INT UNSIGNED NOT NULL,
	CONSTRAINT cars_id PRIMARY KEY(id),
    CONSTRAINT uk_cars_vin UNIQUE (vin),
	CONSTRAINT fk_cars_userId FOREIGN KEY(userId) REFERENCES user(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS repairs(
		id INT UNSIGNED AUTO_INCREMENT,
		kilometers INT NOT NULL,
        repairdate DATETIME default Current_Timestamp,
        repairdescription VARCHAR(100) NOT NULL,
        state VARCHAR(100) NOT NULL,
        repairtype VARCHAR(100) NOT NULL,
        carId INT UNSIGNED NOT NULL,
        contributorId INT UNSIGNED NOT NULL,
	CONSTRAINT repairs_id PRIMARY KEY(id),
	CONSTRAINT fk_repairs_carId FOREIGN KEY(carId) REFERENCES cars(id),
    CONSTRAINT fk_repairs_contributorId FOREIGN KEY(contributorId) REFERENCES contributors(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS schedules(
		id INT UNSIGNED AUTO_INCREMENT,
        currentdate DATETIME default Current_Timestamp,
        schedulingdate DATETIME not null,
        repairdescription VARCHAR(100) NOT NULL,
        state VARCHAR(100) NOT NULL,
        repairtype VARCHAR(100) NOT NULL,
        carId INT UNSIGNED NOT NULL,
        companyId INT UNSIGNED NOT NULL,
	CONSTRAINT schedules_id PRIMARY KEY(id),
	CONSTRAINT fk_schedules_carId FOREIGN KEY(carId) REFERENCES cars(id),
    CONSTRAINT fk_schedules_companyId FOREIGN KEY(companyId) REFERENCES companies(id)
) ENGINE=InnoDB;