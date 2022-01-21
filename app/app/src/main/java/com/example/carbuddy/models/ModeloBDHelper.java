package com.example.carbuddy.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.DatePicker;

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
                        "(id INTEGER PRIMARY KEY, " +
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
                        "(id INTEGER PRIMARY KEY, " +
                        "carId INTEGER NOT NULL, " +
                        "currentdate DATETIME NOT NULL, " +
                        "schedulingdate DATETIME NOT NULL, " +
                        "repairdescription VARCHAR(100) NOT NULL, " +
                        "state VARCHAR(100) NOT NULL," +
                        "repairtype VARCHAR(100) NOT NULL," +
                        "companyId INTEGER NOT NULL" +
                        ");";
        db.execSQL(createSchedulesTable);

        String createCompaniesTable =
                "CREATE TABLE IF NOT EXISTS companies" +
                        "(id INTEGER PRIMARY KEY, " +
                        "companyname VARCHAR(100) NOT NULL," +
                        "nif VARCHAR(9) NOT NULL, " +
                        "email VARCHAR(100) NOT NULL," +
                        "phonenumber VARCHAR(40) NOT NULL," +
                        "registrationdate DATETIME  " +
                        ");";
        db.execSQL(createCompaniesTable);


        String createRepairsTable =
                "CREATE TABLE IF NOT EXISTS repairs(" +
                        "id INTEGER PRIMARY KEY," +
                        "kilometers INTEGER NOT NULL," +
                        "repairdate DATETIME," +
                        "repairdescription VARCHAR(100) NOT NULL," +
                        "state VARCHAR(100) NOT NULL," +
                        "repairtype VARCHAR(100) NOT NULL," +
                        "carId INTEGER NOT NULL," +
                        "contributorId INTEGER NOT NULL," +
                        "CONSTRAINT fk_repairs_carId FOREIGN KEY(carId) REFERENCES cars(id)," +
                        "CONSTRAINT fk_repairs_contributorId FOREIGN KEY(contributorId) REFERENCES contributors(id)" +
                        ");";

        db.execSQL(createRepairsTable);

            String createLoginTable =
                    "CREATE TABLE IF NOT EXISTS login(" +
                            "id INTEGER PRIMARY KEY," +
                            "token TEXT NOT NULL," +
                            "username VARCHAR(100) NOT NULL," +
                            "email VARCHAR(100) NOT NULL," +
                            "nif VARCHAR(9) NOT NULL," +
                            "phonenumber VARCHAR(40) NOT NULL," +
                            "birsthday DATETIME NOT NULL" +
                            ");";
            db.execSQL(createLoginTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //CRUD Cars
    public void insertCars(Car car) {
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

        if (!verificarCar(car, values)) {
            database.insert("cars", null, values);
        }
    }

    private boolean verificarCar(Car car, ContentValues values) {
        return this.database.update("cars", values,
                "id = ? OR vin = ?", new String[]{"" + car.getId(), "" + car.getVin()}) > 0;
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
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getString(11),
                        cursor.getInt(12)
                ));
            } while (cursor.moveToNext());
        }
        return cars;
    }

    public boolean deleteCar(int id)
    {
        return database.delete("cars", "id" + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    //CRUD Companies
    public void insertCompanies(Company company) {
        ContentValues values = new ContentValues();
        values.put("id", company.getId());
        values.put("companyname", company.getCompanyName());
        values.put("nif", company.getNif());
        values.put("email", company.getEmail());
        values.put("phonenumber", company.getPhoneNumber());
        values.put("registrationdate", company.getRegistrationDate());

        if (!verificarCompany(company, values)) {
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


    //CRUD Schedules
    public void insertSchedules(Schedule schedule) {
        ContentValues values = new ContentValues();
        values.put("id", schedule.getId());
        values.put("carId", schedule.getCarId());
        values.put("currentdate", schedule.getCurrentdate());
        values.put("schedulingdate", schedule.getSchedulingdate());
        values.put("repairdescription", schedule.getRepairdescription());
        values.put("state", schedule.getState());
        values.put("repairtype", schedule.getRepairtype());
        values.put("companyId", schedule.getCompanyId());

        if (!verificarSchedule(schedule, values)) {
            database.insert("schedules", null, values);
        }
    }


    private boolean verificarSchedule(Schedule schedule, ContentValues values) {
        return this.database.update("schedules", values,
                "id = ?", new String[]{"" + schedule.getId()}) > 0;
    }


    public LinkedList<Schedule> getAllSchedules() {
        LinkedList<Schedule> schedules = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM schedules ORDER BY schedulingdate DESC",
                null);
        if (cursor.moveToFirst()) {
            do {
                schedules.add(new Schedule(cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7)

                ));
            } while (cursor.moveToNext());
        }
        return schedules;
    }

    //CRUD Repairs
    public void insertRepairs(Repair repair) {
        ContentValues values = new ContentValues();
        values.put("id", repair.getId());
        values.put("kilometers", repair.getKilometers());
        values.put("repairdate", repair.getRepairDate());
        values.put("repairdescription", repair.getRepairDescription());
        values.put("state", repair.getState());
        values.put("repairtype", repair.getRepairtype());
        values.put("carId", repair.getCarId());
        values.put("contributorId", repair.getContributorId());

        if (!verificarRepairs(repair, values)) {
            database.insert("repairs", null, values);
        }
    }

    private boolean verificarRepairs(Repair repair, ContentValues values) {
        return this.database.update("repairs", values,
                "id = ?", new String[]{"" + repair.getId()}) > 0;
    }

    public LinkedList<Repair> getAllRepairs(int carId) {
        LinkedList<Repair> repairs = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM repairs WHERE carId = ?",
                new String[]{String.valueOf(carId)});
        if (cursor.moveToFirst()) {
            do {
                repairs.add(new Repair(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            } while (cursor.moveToNext());
        }
        return repairs;
    }



    //CRUD Login

    public void insertLogin(Login login) {
        ContentValues values = new ContentValues();
        values.put("id", login.getId());
        values.put("token", login.getToken());
        values.put("username", login.getUsername());
        values.put("email", login.getEmail());
        values.put("nif", login.getNif());
        values.put("phonenumber", login.getPhonenumber());
        values.put("birsthday", String.valueOf(login.getBirsthday()));
    //String.valueOf -> Conversão para String

        if (!verificarLogin(login, values)) {
            database.insert("login", null, values);
        }
    }

    private boolean verificarLogin(Login login, ContentValues values) {
        return this.database.update("login", values,
                "token = ?", new String[]{"" + login.getToken()}) > 0;
    }

    public LinkedList<Login> getAllLogin() {
        LinkedList<Login> login = new LinkedList<>();
        Cursor cursor = this.database.rawQuery("SELECT * FROM login",
                null);
        if (cursor.moveToFirst()) {
            do {
                login.add(new Login(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
            } while (cursor.moveToNext());
        }
        return login;
    }
}




