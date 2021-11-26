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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

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

    public void insertComapanies(Company company){
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
}
