package com.example.carbuddy.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

public class ModeloBDHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "carbuddy";
    private static final int DB_VERSION = 1;
    private final SQLiteDatabase database;

    //alterar para apenas receber o contexto, e o factory fica a null
    public ModeloBDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }


    //DB Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCarsTable =
                "CREATE TABLE IF NOT EXISTS cars" +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "vin VARCHAR(100) NOT NULL UNIQUE, " +
                        "brand VARCHAR(100) NOT NULL," +
                        "model VARCHAR(100) NOT NULL," +
                        "color VARCHAR(100) NOT NULL," +
                        "carType VARCHAR(100) NOT NULL," +
                        "displacement FLOAT NOT NULL," +
                        "fuelType VARCHAR(100) NOT NULL," +
                        "registration VARCHAR(100) NOT NULL," +
                        "modelyear YEAR(4) NOT NULL," +
                        "kilometers INT NOT NULL," +
                        "state VARCHAR(100) NOT NULL," +
                        "userId INT NOT NULL," +
                        "FOREIGN KEY (userId) REFERENCES user(id)" +
                        ");";
        db.execSQL(createCarsTable);

        String createSchedulesTable =
                "CREATE TABLE IF NOT EXISTS schedules" +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "currentdate DATETIME default Current_Timestamp, " +
                        "schedulingdate DATETIME NOT NULL, " +
                        "repairdescription VARCHAR(100) NOT NULL, " +
                        "state VARCHAR(100) NOT NULL," +
                        "repairtype VARCHAR(100) NOT NULL," +
                        "carId INT NOT NULL," +
                        "companyId INT NOT NULL," +
                        "FOREIGN KEY (carId) REFERENCES car(id)," +
                        "FOREIGN KEY (companyId) REFERENCES company(id)" +
                        ");";
        db.execSQL(createSchedulesTable);

        String createCompaniesTable =
                "CREATE TABLE IF NOT EXISTS companies" +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "companyname VARCHAR(100) NOT NULL," +
                        "nif VARCHAR(9) NOT NULL, " +
                        "email VARCHAR(100) NOT NULL," +
                        "phonenumber VARCHAR(40) NOT NULL," +
                        "registrationdate DATETIME default Current_Timestamp " +
                        ");";
        db.execSQL(createCompaniesTable);

        //Notas: Unsigned dá erro;
        String createUserTable =
                "CREATE TABLE IF NOT EXISTS user" +
                        "(id INT UNSIGNED AUTO_INCREMENT, " +
                        "username VARCHAR(100) NOT NULL," +
                        "password_hash TEXT NOT NULL," +
                        "verification_token TEXT NOT NULL," +
                        "auth_key TEXT NOT NULL," +
                        "status INT NOT NULL," +
                        "updated_at INT NOT NULL," +
                        "created_at INT NOT NULL," +
                        "usertype VARCHAR(100) NOT NULL," +
                        "nif VARCHAR(9) NOT NULL," +
                        "birsthday DATE NOT NULL," +
                        "email VARCHAR(100) NOT NULL," +
                        "phonenumber VARCHAR(40) NOT NULL," +
                        "registrationdate DATETIME default Current_Timestamp," +
                                "CONSTRAINT users_id PRIMARY KEY(id)," +
                                "CONSTRAINT uk_users_Nif UNIQUE (nif)" +
                        ") ENGINE=InnoDB;";

        db.execSQL(createUserTable);

        String createContributorsTable =
                "CREATE TABLE IF NOT EXISTS contributors(" +
                        "id INT UNSIGNED AUTO_INCREMENT," +
                        "speciality VARCHAR(100) NOT NULL," +
                        "companyId INT UNSIGNED NOT NULL," +
                        "userId INT UNSIGNED NOT NULL," +
                    "CONSTRAINT contributors_id PRIMARY KEY(id)," +
                    "CONSTRAINT fk_contributors_companyId FOREIGN KEY(companyId) REFERENCES companies(id)," +
                    "CONSTRAINT fk_contributors_userId FOREIGN KEY(userId) REFERENCES user(id)," +
                    "CONSTRAINT uk_contributors_userId UNIQUE (userId)" +
                ") ENGINE=InnoDB;";

        db.execSQL(createContributorsTable);


        String createRepairsTable =
                "CREATE TABLE IF NOT EXISTS repairs(" +
                            "id INT UNSIGNED AUTO_INCREMENT," +
                            "kilometers INT NOT NULL," +
                            "repairdate DATETIME default Current_Timestamp," +
                            "repairdescription VARCHAR(100) NOT NULL," +
                            "state VARCHAR(100) NOT NULL," +
                            "repairtype VARCHAR(100) NOT NULL," +
                            "carId INT UNSIGNED NOT NULL," +
                            "contributorId INT UNSIGNED NOT NULL," +
                        "CONSTRAINT repairs_id PRIMARY KEY(id)," +
                        "CONSTRAINT fk_repairs_carId FOREIGN KEY(carId) REFERENCES cars(id)," +
                        "CONSTRAINT fk_repairs_contributorId FOREIGN KEY(contributorId) REFERENCES contributors(id)" +
                ") ENGINE=InnoDB;";

        db.execSQL(createRepairsTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //CRUD Cars
    public void insertCars(Car car){
        ContentValues values = new ContentValues();
        values.put("id", car.getId());
        values.put("vin", car.getVin());
        values.put("brand", car.getBrand());
        values.put("model", car.getModel());
        values.put("color", car.getColor());
        values.put("carType", car.getCartype());
        values.put("displacement", car.getDisplacement());
        values.put("fuelType", car.getFueltype());
        values.put("registration", car.getRegistration());
        values.put("modelyear", car.getModelyear());
        values.put("kilometers", car.getKilometers());
        values.put("state", car.getState());
        values.put("userId", car.getUserId());

        if(!verificarCar(car, values)) {
            database.insert("cars", null, values);
        }
    }

    private boolean verificarCar(Car car, ContentValues values) {
        return this.database.update("cars", values,
                "id = ?", new String[]{"" + car.getId()}) > 0;
    }

    public LinkedList<Car> getAllCars() {
        LinkedList<Car> cars = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM cars",
                null);
        if (cursor.moveToFirst()) {
            do {
                cars.add(new Car(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getFloat(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getInt(10),
                        cursor.getString(11),
                        cursor.getInt(12)
                        ));
            } while (cursor.moveToNext());
        }
        return cars;
    }


    //CRUD Companies
    public void insertCompanies(Company company){
        ContentValues values = new ContentValues();
        values.put("id", company.getId());
        values.put("companyname", company.getCompanyName());
        values.put("nif", company.getNif());
        values.put("email", company.getEmail());
        values.put("phonenumber", company.getPhoneNumber());
        values.put("registrationdate", company.getRegistrationDate());

        if(!verificarCompany(company, values)) {
            database.insert("companies", null, values);
        }
    }

    private boolean verificarCompany(Company company, ContentValues values) {
        return this.database.update("companies", values,
                "id = ?", new String[]{"" + company.getId()}) > 0;
    }

    public LinkedList<Company> getAllCompanies() {
        LinkedList<Company> companies = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM companies",
                null);
        if (cursor.moveToFirst()) {
            do {
                companies.add(new Company(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
            } while (cursor.moveToNext());
        }
        return companies;
    }



//CRUD Schedules  String createSchedulesTable =
//                "CREATE TABLE IF NOT EXISTS schedules" +
//                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        "currentdate DATETIME default Current_Timestamp, " +
//                        "schedulingdate DATETIME NOT NULL, " +
//                        "repairdescription VARCHAR(100) NOT NULL, " +
//                        "state VARCHAR(100) NOT NULL," +
//                        "repairtype VARCHAR(100) NOT NULL," +
//                        "carId INT NOT NULL," +
//                        "companyId INT NOT NULL," +
//                        "FOREIGN KEY (carId) REFERENCES car(id)," +
//                        "FOREIGN KEY (companyId) REFERENCES company(id)" +
//                        ");";
//        db.execSQL(createSchedulesTable);

    public void insertSchedules(Schedule schedule){
        ContentValues values = new ContentValues();
        values.put("id", schedule.getId());
        values.put("currentdate", schedule.getCurrentDate());
        values.put("schedulingdate", schedule.getSchedulingDate());
        values.put("repairdescription", schedule.getRepairDescription());
        values.put("state", schedule.getState());
        values.put("repairtype", schedule.getRepairType());
        values.put("carId", schedule.getCarId());
        values.put("companyId",schedule.getCompanyId());

        if(!verificarSchedule(schedule, values)) {
            database.insert("schedules", null, values);
        }
    }


    private boolean verificarSchedule(Schedule schedule, ContentValues values) {
        return this.database.update("schedules", values,
                "id = ?", new String[]{"" + schedule.getId()}) > 0;
    }


    public LinkedList<Schedule> getAllSchedules() {
        LinkedList<Schedule> schedules = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM schedules",
                null);
        if (cursor.moveToFirst()) {
            do {
                //schedules.add(new Schedule(cursor.getInt(0),
                        //Código para testar:
                        //Cursor row = databaseHelper.query(true, TABLE_NAME, new String[] {
                        //COLUMN_INDEX}, ID_COLUMN_INDEX + "=" + rowId,
                        //null, null, null, null, null);
                        //String dateTime = row.getString(row.getColumnIndexOrThrow(COLUMN_INDEX));
                        //Tentei mas não funcionou -> cursor.getCurrentDate(1),
                        //cursor.getString(2),
                        //cursor.getString(3),
                        //cursor.getString(4),
                        //cursor.getString(5),
                        //cursor.getFloat(6),
                        //cursor.getString(7),

                //));
            } while (cursor.moveToNext());
        }
        return schedules;
    }


//CRUD Users "CREATE TABLE IF NOT EXISTS user" +
//                        "(id INT UNSIGNED AUTO_INCREMENT, " +
//                        "username VARCHAR(100) NOT NULL," +
//                        "password_hash TEXT NOT NULL," +
//                        "verification_token TEXT NOT NULL," +
//                        "auth_key TEXT NOT NULL," +
//                        "status INT NOT NULL," +
//                        "updated_at INT NOT NULL," +
//                        "created_at INT NOT NULL," +
//                        "usertype VARCHAR(100) NOT NULL," +
//                        "nif VARCHAR(9) NOT NULL," +
//                        "birsthday DATE NOT NULL," +
//                        "email VARCHAR(100) NOT NULL," +
//                        "phonenumber VARCHAR(40) NOT NULL," +
//                        "registrationdate DATETIME default Current_Timestamp," +
//                                "CONSTRAINT users_id PRIMARY KEY(id)," +
//                                "CONSTRAINT uk_users_Nif UNIQUE (nif)" +
//                        ") ENGINE=InnoDB;";


    public void insertUsers(User user){
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("username", user.getUsername());
        values.put("password_hash", user.getUserPassword());
        values.put("usertype", user.getUserType());
        values.put("nif", user.getNif());
        values.put("birthday", user.getBirthday());
        values.put("phonenumber", user.getPhoneNumber());
        values.put("registrationdate", user.getRegistrationDate());

        if(!verificarUser(user, values)) {
            database.insert("user", null, values);
        }
    }

    private boolean verificarUser(User user, ContentValues values) {
        return this.database.update("user", values,
                "id = ?", new String[]{"" + user.getId()}) > 0;
    }

    public LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM user",
                null);
        /*if (cursor.moveToFirst()) {
            do {
                users.add(new User(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        Data?cursor.(5),
                        cursor.getString(6),
                        Data?cursor.(7),
                ));
            } while (cursor.moveToNext());
        }*/
        return users;
    }

    