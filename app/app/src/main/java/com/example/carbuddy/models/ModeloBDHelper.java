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


    /**
     * DB Tables CREATE
     **/
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
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * DB Tables INSERT CAR
     **/
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

    /**
     * Verificar carro na DB
     **/
    private boolean verificarCar(Car car, ContentValues values) {
        return this.database.update("cars", values,
                "id = ? OR vin = ?", new String[]{"" + car.getId(), "" + car.getVin()}) > 0;
    }

    /**
     * Obter todos os carros registados
     **/
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

    /**
     * Apagar carro na DB
     **/
    public boolean deleteCar(int id) {
        return database.delete("cars", "id" + "=?", new String[]{String.valueOf(id)}) > 0;
    }


    /**
     * DB Tables INSERT Company
     **/
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

    /**
     * Verificar company na DB
     **/
    private boolean verificarCompany(Company company, ContentValues values) {
        return this.database.update("companies", values,
                "id = ?", new String[]{"" + company.getId()}) > 0;
    }

    /**
     * Obter todas as companies registadas
     **/
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


    /**
     * DB Tables INSERT Schedules
     **/
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

    /**
     * Verificar schedules na DB
     **/
    private boolean verificarSchedule(Schedule schedule, ContentValues values) {
        return this.database.update("schedules", values,
                "id = ?", new String[]{"" + schedule.getId()}) > 0;
    }

    /**
     * Obter todas as schedules registadas
     **/
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

    /**
     * DB Tables INSERT Repairs
     **/
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

    /**
     * Verificar Repair na DB
     **/
    private boolean verificarRepairs(Repair repair, ContentValues values) {
        return this.database.update("repairs", values,
                "id = ?", new String[]{"" + repair.getId()}) > 0;
    }

    /**
     * Obter todas as Repairs registadas
     **/
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
}




