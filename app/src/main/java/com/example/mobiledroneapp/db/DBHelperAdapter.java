package com.example.mobiledroneapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.mobiledroneapp.helpers.StaticCONSTANTUtils;
import com.example.mobiledroneapp.helpers.ToastHelper;
import com.example.mobiledroneapp.models.Assignments;
import com.example.mobiledroneapp.models.FlightLogger;
import com.example.mobiledroneapp.models.Owner;
import com.example.mobiledroneapp.models.StatusLogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DBHelperAdapter  extends SQLiteOpenHelper  {

    public DBHelperAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_version);
    }

    private static final String DATABASE_NAME = "droneDatabase_v4";

    private static final int DATABASE_version = 2;  // era 2

    private static final String CREATE_OWNER_TABLE = "CREATE TABLE " + StaticCONSTANTUtils.TABLE_NAME_OWNER
            + " (" + StaticCONSTANTUtils.OWNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StaticCONSTANTUtils.OWNER_NAME + " VARCHAR(255) ,"
            + StaticCONSTANTUtils.OWNER_EMAIL + " VARCHAR(255) ,"
            + StaticCONSTANTUtils.OWNER_PASS + " VARCHAR(255) ,"
            + StaticCONSTANTUtils.OWNER_CREATION_DATE + " TEXT ,"
            + StaticCONSTANTUtils.OWNER_LAST_LOGIN+ " TEXT ,"
            + StaticCONSTANTUtils.OWNER_HOME + " VARCHAR(255));";
    private static final String DROP_OWNER_TABLE = "DROP TABLE IF EXISTS " + StaticCONSTANTUtils.TABLE_NAME_OWNER;


    private static final String CREATE_STATUS_LOGGER_TABLE = "CREATE TABLE " + StaticCONSTANTUtils.TABLE_NAME_STATUS_LOGGER
            + " (" + StaticCONSTANTUtils.S_LOGGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StaticCONSTANTUtils.S_LOGGER_FLIGHT_ID + " INTEGER ,"
            + StaticCONSTANTUtils.S_LOGGER_STATUS + " VARCHAR(255) ,"
            + StaticCONSTANTUtils.S_LOGGER_DATE + " TEXT ,"
            + StaticCONSTANTUtils.S_LOGGER_RISK+ " VARCHAR(255) ,"
            + StaticCONSTANTUtils.S_LOGGER_BATTERY + " INTEGER);";
    private static final String DROP_STATUS_LOGGER_TABLE = "DROP TABLE IF EXISTS " + StaticCONSTANTUtils.TABLE_NAME_STATUS_LOGGER;

    private static final String CREATE_FLIGHT_LOGGER_TABLE = "CREATE TABLE " + StaticCONSTANTUtils.TABLE_NAME_FLIGHT_LOGGER
            + " (" + StaticCONSTANTUtils.F_LOGGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StaticCONSTANTUtils.F_LOGGER_ASSIGNMENTS_ID + " INTEGER ,"
            + StaticCONSTANTUtils.F_LOGGER_LAT + " REAL ,"
            + StaticCONSTANTUtils.F_LOGGER_LNG + " REAL ,"
            + StaticCONSTANTUtils.F_LOGGER_ALTITUDE + " REAL);";
    private static final String DROP_FLIGHT_LOGGER_TABLE = "DROP TABLE IF EXISTS " + StaticCONSTANTUtils.TABLE_NAME_FLIGHT_LOGGER;

    private static final String CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE " + StaticCONSTANTUtils.TABLE_NAME_ASSIGNMENTS
            + " (" + StaticCONSTANTUtils.ASSIGNMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StaticCONSTANTUtils.ASSIGNMENTS_START_DATE + " TEXT ,"
            + StaticCONSTANTUtils.ASSIGNMENTS_END_DATE + " TEXT ,"
            + StaticCONSTANTUtils.ASSIGNMENTS_MODE + " VARCHAR(255) ,"
            + StaticCONSTANTUtils.ASSIGNMENTS_FLIGHT_DURATION + " INTEGER);";
    private static final String DROP_ASSIGNMENTS_TABLE = "DROP TABLE IF EXISTS " + StaticCONSTANTUtils.TABLE_NAME_ASSIGNMENTS;

    private Context context;

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_OWNER_TABLE);
            db.execSQL(CREATE_STATUS_LOGGER_TABLE);
            db.execSQL(CREATE_FLIGHT_LOGGER_TABLE);
            db.execSQL(CREATE_ASSIGNMENTS_TABLE);
        } catch (Exception e) {
            ToastHelper.message(context, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            ToastHelper.message(context, "OnUpgrade");
            db.execSQL(DROP_OWNER_TABLE);
            db.execSQL(CREATE_STATUS_LOGGER_TABLE);
            db.execSQL(CREATE_FLIGHT_LOGGER_TABLE);
            db.execSQL(CREATE_ASSIGNMENTS_TABLE);
            onCreate(db);
        } catch (Exception e) {
            ToastHelper.message(context, "" + e);
        }
    }

    // *********************************************************************************************

    public long insertINOwners(Owner owner) {
        //"ID", "EMAIL", "NAME", "PASSWORD", "CREATE_DATE", "LAST_LOGIN", "HOME"
        SQLiteDatabase dbb = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StaticCONSTANTUtils.OWNER_EMAIL, owner.getEmail());
        contentValues.put(StaticCONSTANTUtils.OWNER_NAME, owner.getName());
        contentValues.put(StaticCONSTANTUtils.OWNER_PASS, owner.getPassword());
        contentValues.put(StaticCONSTANTUtils.OWNER_CREATION_DATE, owner.getCreate_date());
        contentValues.put(StaticCONSTANTUtils.OWNER_LAST_LOGIN, owner.getLast_login());
        contentValues.put(StaticCONSTANTUtils.OWNER_HOME, owner.getHome());
        long id = dbb.insert(StaticCONSTANTUtils.TABLE_NAME_OWNER, null, contentValues);
        dbb.close();
        return id;
    }

    public List<Owner> getOwners() {
        //"ID", "EMAIL", "NAME", "PASSWORD", "CREATE_DATE", "LAST_LOGIN", "HOME"
        List<Owner> owners = new ArrayList<>();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.OWNER_EMAIL,
                StaticCONSTANTUtils.OWNER_NAME,
                StaticCONSTANTUtils.OWNER_PASS,
                StaticCONSTANTUtils.OWNER_CREATION_DATE,
                StaticCONSTANTUtils.OWNER_LAST_LOGIN,
                StaticCONSTANTUtils.OWNER_HOME
        };
        String sql = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_OWNER;
        Cursor cursor = dbb.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                Owner owner = new Owner();
                owner.setId(Integer.parseInt(cursor.getString(0)));
                owner.setEmail(cursor.getString(1));
                owner.setName(cursor.getString(2));
                owner.setPassword(cursor.getString(3));
                owner.setCreate_date(cursor.getString(4));
                owner.setLast_login(cursor.getString(5));
                owner.setHome(cursor.getString(6));
                owners.add(owner);
            } while(cursor.moveToNext());

        }
        cursor.close();
        dbb.close();
        return owners;
    }

    public int getNoOfOwners() {
        String count = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_OWNER;
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor cursor = dbb.rawQuery(count, null);
        cursor.close();
        dbb.close();
        return cursor.getCount();
    }

    public Owner getOwner(String email) {
        Owner owner = new Owner();
        if(!email.equals("")) {
            SQLiteDatabase dbb = this.getReadableDatabase();
            String columns[] = {
                    StaticCONSTANTUtils.OWNER_ID,
                    StaticCONSTANTUtils.OWNER_EMAIL,
                    StaticCONSTANTUtils.OWNER_NAME,
                    StaticCONSTANTUtils.OWNER_PASS,
                    StaticCONSTANTUtils.OWNER_CREATION_DATE,
                    StaticCONSTANTUtils.OWNER_LAST_LOGIN,
                    StaticCONSTANTUtils.OWNER_HOME
            };
            Cursor cursor = dbb.query(StaticCONSTANTUtils.TABLE_NAME_OWNER,
                    columns, StaticCONSTANTUtils.OWNER_COLUMNS[1] + "=?",
                    new String[] { email }, null, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
            }
            owner.setId(Integer.parseInt(cursor.getString(0)));
            owner.setEmail(cursor.getString(1));
            owner.setName(cursor.getString(2));
            owner.setPassword(cursor.getString(3));
            owner.setCreate_date(cursor.getString(4));
            owner.setLast_login(cursor.getString(5));
            owner.setHome(cursor.getString(6));
            cursor.close();
            dbb.close();
        }
        return owner;
    }

    public void updateLastLogin(int ownerId) {
        SQLiteDatabase dbb = this.getReadableDatabase();
        String columns[] = { StaticCONSTANTUtils.OWNER_ID,
                StaticCONSTANTUtils.OWNER_LAST_LOGIN };
        String timeFormat = "yyyy-mm-dd HH:MM:SS.SSS";
        Date dateNow = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(timeFormat);

        String _creationDate = dateFormat.format(dateNow);
        ContentValues cv = new ContentValues();
        cv.put(columns[1], _creationDate);
        dbb.update(StaticCONSTANTUtils.TABLE_NAME_OWNER, cv, columns[0] + "=?",
                new String[] { ownerId + ""});
        dbb.close();
    }

    // *********************************************************************************************

    public long insertInStatusLogger(StatusLogger statusLogger) {
        // ID, FLIGHT_ID, STATUS, DATE, RISK, BATTERY
        SQLiteDatabase dbb = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StaticCONSTANTUtils.S_LOGGER_FLIGHT_ID, statusLogger.getFlightId());
        contentValues.put(StaticCONSTANTUtils.S_LOGGER_STATUS, statusLogger.getStatus());
        contentValues.put(StaticCONSTANTUtils.S_LOGGER_DATE, statusLogger.getDate());
        contentValues.put(StaticCONSTANTUtils.S_LOGGER_RISK, statusLogger.getRisk());
        contentValues.put(StaticCONSTANTUtils.S_LOGGER_BATTERY, statusLogger.getBattery());
        long id = dbb.insert(StaticCONSTANTUtils.TABLE_NAME_STATUS_LOGGER, null, contentValues);
        dbb.close();
        return id;
    }

    public List<StatusLogger> getStatusLogger() {
        // ID, FLIGHT_ID, STATUS, DATE, RISK, BATTERY
        List<StatusLogger> statusLogs = new ArrayList<>();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.S_LOGGER_ID,
                StaticCONSTANTUtils.S_LOGGER_FLIGHT_ID,
                StaticCONSTANTUtils.S_LOGGER_STATUS,
                StaticCONSTANTUtils.S_LOGGER_DATE,
                StaticCONSTANTUtils.S_LOGGER_RISK,
                StaticCONSTANTUtils.S_LOGGER_BATTERY
        };
        String sql = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_STATUS_LOGGER;
        Cursor cursor = dbb.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                StatusLogger statusLogger = new StatusLogger();
                statusLogger.setId(Integer.parseInt(cursor.getString(0)));
                statusLogger.setFlightId(Integer.parseInt(cursor.getString(1)));
                statusLogger.setStatus(cursor.getString(2));
                statusLogger.setDate(cursor.getString(3));
                statusLogger.setRisk(cursor.getString(4));
                statusLogger.setBattery(Integer.parseInt(cursor.getString(5)));
                statusLogs.add(statusLogger);
            } while(cursor.moveToNext());
        }
        cursor.close();
        dbb.close();
        return statusLogs;
    }

    public int getNoOfStatusLoggers() {
        String count = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_STATUS_LOGGER;
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor cursor = dbb.rawQuery(count, null);
        cursor.close();
        dbb.close();
        return cursor.getCount();
    }

    public StatusLogger getStatusLogger(int id) {
        StatusLogger statusLogger = new StatusLogger();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.S_LOGGER_ID,
                StaticCONSTANTUtils.S_LOGGER_FLIGHT_ID,
                StaticCONSTANTUtils.S_LOGGER_STATUS,
                StaticCONSTANTUtils.S_LOGGER_DATE,
                StaticCONSTANTUtils.S_LOGGER_RISK,
                StaticCONSTANTUtils.S_LOGGER_BATTERY
        };
        Cursor cursor = dbb.query(StaticCONSTANTUtils.TABLE_NAME_STATUS_LOGGER,
                columns, StaticCONSTANTUtils.S_LOGGER_ID + "=?",
                new String[] { id + ""}, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        statusLogger.setId(Integer.parseInt(cursor.getString(0)));
        statusLogger.setFlightId(Integer.parseInt(cursor.getString(1)));
        statusLogger.setStatus(cursor.getString(2));
        statusLogger.setDate(cursor.getString(3));
        statusLogger.setRisk(cursor.getString(4));
        statusLogger.setBattery(Integer.parseInt(cursor.getString(5)));
        cursor.close();
        dbb.close();
        return statusLogger;
    }

    public StatusLogger getStatusLoggerByFlightLog(int id) {
        StatusLogger statusLogger = new StatusLogger();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.S_LOGGER_ID,
                StaticCONSTANTUtils.S_LOGGER_FLIGHT_ID,
                StaticCONSTANTUtils.S_LOGGER_STATUS,
                StaticCONSTANTUtils.S_LOGGER_DATE,
                StaticCONSTANTUtils.S_LOGGER_RISK,
                StaticCONSTANTUtils.S_LOGGER_BATTERY
        };
        Cursor cursor = dbb.query(StaticCONSTANTUtils.TABLE_NAME_STATUS_LOGGER,
                columns, StaticCONSTANTUtils.S_LOGGER_FLIGHT_ID + "=?",
                new String[] { id + ""}, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        statusLogger.setId(Integer.parseInt(cursor.getString(0)));
        statusLogger.setFlightId(Integer.parseInt(cursor.getString(1)));
        statusLogger.setStatus(cursor.getString(2));
        statusLogger.setDate(cursor.getString(3));
        statusLogger.setRisk(cursor.getString(4));
        statusLogger.setBattery(Integer.parseInt(cursor.getString(5)));
        cursor.close();
        dbb.close();
        return statusLogger;
    }

    // *********************************************************************************************

    public long insertInFlightLogger(FlightLogger flightLogger) {
        // ID, ASSIGNMENT_ID, LAT, LNG, ALTITUDE
        SQLiteDatabase dbb = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StaticCONSTANTUtils.F_LOGGER_ASSIGNMENTS_ID, flightLogger.getAssignmentId());
        contentValues.put(StaticCONSTANTUtils.F_LOGGER_LAT, flightLogger.getLat());
        contentValues.put(StaticCONSTANTUtils.F_LOGGER_LNG, flightLogger.getLng());
        contentValues.put(StaticCONSTANTUtils.F_LOGGER_ALTITUDE, flightLogger.getAltitude());
        long id = dbb.insert(StaticCONSTANTUtils.TABLE_NAME_FLIGHT_LOGGER, null, contentValues);
        dbb.close();
        return id;
    }

    public List<FlightLogger> getFlightLoggers() {
        // ID, ASSIGNMENT_ID, LAT, LNG, ALTITUDE
        List<FlightLogger> flightLogs = new ArrayList<>();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.F_LOGGER_ID,
                StaticCONSTANTUtils.F_LOGGER_ASSIGNMENTS_ID,
                StaticCONSTANTUtils.F_LOGGER_LAT,
                StaticCONSTANTUtils.F_LOGGER_LNG,
                StaticCONSTANTUtils.F_LOGGER_ALTITUDE
        };
        String sql = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_FLIGHT_LOGGER;
        Cursor cursor = dbb.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                FlightLogger flightLogger = new FlightLogger();
                flightLogger.setId(Integer.parseInt(cursor.getString(0)));
                flightLogger.setAssignmentId(Integer.parseInt(cursor.getString(1)));
                flightLogger.setLat(Long.parseLong(cursor.getString(2)));
                flightLogger.setLng(Long.parseLong(cursor.getString(3)));
                flightLogger.setAltitude(Long.parseLong(cursor.getString(4)));
                flightLogs.add(flightLogger);
            } while(cursor.moveToNext());
        }
        cursor.close();
        dbb.close();
        return flightLogs;
    }

    public int getNoOfFlightLoggers() {
        String count = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_FLIGHT_LOGGER;
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor cursor = dbb.rawQuery(count, null);
        cursor.close();
        dbb.close();
        return cursor.getCount();
    }

    public FlightLogger getFlightLogger(int id) {
        FlightLogger flightLogger = new FlightLogger();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.F_LOGGER_ID,
                StaticCONSTANTUtils.F_LOGGER_ASSIGNMENTS_ID,
                StaticCONSTANTUtils.F_LOGGER_LAT,
                StaticCONSTANTUtils.F_LOGGER_LNG,
                StaticCONSTANTUtils.F_LOGGER_ALTITUDE
        };
        Cursor cursor = dbb.query(StaticCONSTANTUtils.TABLE_NAME_FLIGHT_LOGGER,
                columns, StaticCONSTANTUtils.F_LOGGER_ID + "=?",
                new String[] { id + ""}, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        flightLogger.setId(Integer.parseInt(cursor.getString(0)));
        flightLogger.setAssignmentId(Integer.parseInt(cursor.getString(1)));
        flightLogger.setLat(Long.parseLong(cursor.getString(2)));
        flightLogger.setLng(Long.parseLong(cursor.getString(3)));
        flightLogger.setAltitude(Long.parseLong(cursor.getString(4)));
        cursor.close();
        dbb.close();
        return flightLogger;
    }

    public List<FlightLogger> findFlightLoggerByAssignmentID(int id) {
        List<FlightLogger> flightLogs = new ArrayList<>();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.F_LOGGER_ID,
                StaticCONSTANTUtils.F_LOGGER_ASSIGNMENTS_ID,
                StaticCONSTANTUtils.F_LOGGER_LAT,
                StaticCONSTANTUtils.F_LOGGER_LNG,
                StaticCONSTANTUtils.F_LOGGER_ALTITUDE
        };
        Cursor cursor = dbb.query(StaticCONSTANTUtils.TABLE_NAME_FLIGHT_LOGGER,
                columns, StaticCONSTANTUtils.F_LOGGER_ASSIGNMENTS_ID + "=?",
                new String[] { id + ""}, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                FlightLogger flightLogger = new FlightLogger();
                flightLogger.setId(Integer.parseInt(cursor.getString(0)));
                flightLogger.setAssignmentId(Integer.parseInt(cursor.getString(1)));
                flightLogger.setLat(Double.parseDouble(cursor.getString(2)));
                flightLogger.setLng(Double.parseDouble(cursor.getString(3)));
                flightLogger.setAltitude(Long.parseLong(cursor.getString(4)));
                flightLogs.add(flightLogger);
            } while(cursor.moveToNext());
        }
        cursor.close();
        dbb.close();
        return flightLogs;
    }


    // *********************************************************************************************

    public long insertInAssignments(Assignments assignments) {
        // ID, START_DATE, END_DATE, MODE, FLIGHT_DURATION
        SQLiteDatabase dbb = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StaticCONSTANTUtils.ASSIGNMENTS_START_DATE, assignments.getStartDate());
        contentValues.put(StaticCONSTANTUtils.ASSIGNMENTS_END_DATE, assignments.getEndDate());
        contentValues.put(StaticCONSTANTUtils.ASSIGNMENTS_MODE, assignments.getMode());
        contentValues.put(StaticCONSTANTUtils.ASSIGNMENTS_FLIGHT_DURATION, assignments.getFlightDuration());
        long id = dbb.insert(StaticCONSTANTUtils.TABLE_NAME_ASSIGNMENTS, null, contentValues);
        dbb.close();
        return id;
    }

    public List<Assignments> getAssignments() {
        // ID, START_DATE, END_DATE, MODE, FLIGHT_DURATION
        List<Assignments> assignments = new ArrayList<>();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.ASSIGNMENTS_ID,
                StaticCONSTANTUtils.ASSIGNMENTS_START_DATE,
                StaticCONSTANTUtils.ASSIGNMENTS_END_DATE,
                StaticCONSTANTUtils.ASSIGNMENTS_MODE,
                StaticCONSTANTUtils.ASSIGNMENTS_FLIGHT_DURATION
        };
        String sql = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_ASSIGNMENTS;
        Cursor cursor = dbb.rawQuery(sql, null);
        if(cursor.moveToFirst()) {
            do {
                Assignments assignment = new Assignments();
                assignment.setId(Integer.parseInt(cursor.getString(0)));
                assignment.setStartDate(cursor.getString(1));
                assignment.setEndDate(cursor.getString(2));
                assignment.setMode(cursor.getString(3));
                assignment.setFlightDuration(cursor.getString(4));
                assignments.add(assignment);
            } while(cursor.moveToNext());
        }
        cursor.close();
        dbb.close();
        return assignments;
    }

    public int getNoOfAssigments() {
        String count = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_ASSIGNMENTS;
        SQLiteDatabase dbb = this.getReadableDatabase();
        Cursor cursor = dbb.rawQuery(count, null);
        cursor.close();
        dbb.close();
        return cursor.getCount();
    }

    public Assignments getAssignment(int id) {
        Assignments assignment = new Assignments();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.ASSIGNMENTS_ID,
                StaticCONSTANTUtils.ASSIGNMENTS_START_DATE,
                StaticCONSTANTUtils.ASSIGNMENTS_END_DATE,
                StaticCONSTANTUtils.ASSIGNMENTS_MODE,
                StaticCONSTANTUtils.ASSIGNMENTS_FLIGHT_DURATION
        };
        Cursor cursor = dbb.query(StaticCONSTANTUtils.TABLE_NAME_ASSIGNMENTS,
                columns, StaticCONSTANTUtils.ASSIGNMENTS_ID + "=?",
                new String[] { id + ""}, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        assignment.setId(Integer.parseInt(cursor.getString(0)));
        assignment.setStartDate(cursor.getString(1));
        assignment.setEndDate(cursor.getString(2));
        assignment.setMode(cursor.getString(3));
        assignment.setFlightDuration(cursor.getString(4));
        cursor.close();
        dbb.close();
        return assignment;
    }

    public List<Assignments> getAssignmentsByDate(String date) {
        List<Assignments> assignments = new ArrayList<>();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.ASSIGNMENTS_ID,
                StaticCONSTANTUtils.ASSIGNMENTS_START_DATE,
                StaticCONSTANTUtils.ASSIGNMENTS_END_DATE,
                StaticCONSTANTUtils.ASSIGNMENTS_MODE,
                StaticCONSTANTUtils.ASSIGNMENTS_FLIGHT_DURATION
        };
        Cursor cursor = dbb.query(StaticCONSTANTUtils.TABLE_NAME_ASSIGNMENTS,
                columns, StaticCONSTANTUtils.ASSIGNMENTS_START_DATE + "=?",
                new String[] { date}, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                Assignments assignment = new Assignments();
                assignment.setId(Integer.parseInt(cursor.getString(0)));
                assignment.setStartDate(cursor.getString(1));
                assignment.setEndDate(cursor.getString(2));
                assignment.setMode(cursor.getString(3));
                assignment.setFlightDuration(cursor.getString(4));
                assignments.add(assignment);
            } while(cursor.moveToNext());
        }
        cursor.close();
        dbb.close();
       return assignments;
    }

    // *********************************************************************************************


    // POPULATE DUMMY DATA //

    public void populateDummyData() {
        String timeFormat = "mm-dd-yyyy";
        SimpleDateFormat dbFormat = new SimpleDateFormat(timeFormat);
        String _creationDate = dbFormat.format(Calendar.getInstance().getTime());
        Assignments assignment = new Assignments();
        assignment.setStartDate(_creationDate);
        assignment.setEndDate(_creationDate);
        assignment.setFlightDuration("23");
        assignment.setMode("PATROL");
        long assignmentId = insertInAssignments(assignment);

        FlightLogger fl = new FlightLogger();
        fl.setAssignmentId((int)assignmentId);
        fl.setLat(24.35);
        fl.setLng(25.24);
        fl.setAltitude(130);
        long flightLogID = insertInFlightLogger(fl);

        FlightLogger fl1 = new FlightLogger();
        fl.setAssignmentId((int)assignmentId);
        fl.setLat(24.45);
        fl.setLng(25.24);
        fl.setAltitude(110);
        long flightLog1ID = insertInFlightLogger(fl1);

        FlightLogger fl2 = new FlightLogger();
        fl.setAssignmentId((int)assignmentId);
        fl.setLat(24.45);
        fl.setLng(22.34);
        fl.setAltitude(120);
        long flightLog2ID = insertInFlightLogger(fl2);

        StatusLogger sl1 = new StatusLogger();
        sl1.setBattery(90);
        sl1.setRisk("NO RISK");
        sl1.setDate(_creationDate);
        sl1.setFlightId((int) flightLog2ID);
        sl1.setStatus("GOOD");
        insertInStatusLogger(sl1);

        StatusLogger sl2 = new StatusLogger();
        sl2.setBattery(80);
        sl2.setRisk("NO RISK");
        sl2.setDate(_creationDate);
        sl2.setFlightId((int) flightLog2ID);
        sl2.setStatus("GOOD");
        insertInStatusLogger(sl2);

        StatusLogger sl3 = new StatusLogger();
        sl3.setBattery(60);
        sl3.setRisk("NO RISK");
        sl3.setDate(_creationDate);
        sl3.setFlightId((int) flightLog2ID);
        sl3.setStatus("LOW BATTERY");
        insertInStatusLogger(sl3);

        FlightLogger fl3 = new FlightLogger();
        fl.setAssignmentId((int)assignmentId);
        fl.setLat(24.25);
        fl.setLng(25.24);
        fl.setAltitude(120);
        long flightLog3ID = insertInFlightLogger(fl3);


    }

    public Assignments getLastAssignement() {
        Assignments assignment = new Assignments();
        SQLiteDatabase dbb = getWritableDatabase();
        String columns[] = {
                StaticCONSTANTUtils.ASSIGNMENTS_ID,
                StaticCONSTANTUtils.ASSIGNMENTS_START_DATE,
                StaticCONSTANTUtils.ASSIGNMENTS_END_DATE,
                StaticCONSTANTUtils.ASSIGNMENTS_MODE,
                StaticCONSTANTUtils.ASSIGNMENTS_FLIGHT_DURATION
        };
        String sql = "SELECT * FROM " + StaticCONSTANTUtils.TABLE_NAME_ASSIGNMENTS
                + " ORDER BY ID DESC LIMIT 1";
        Cursor cursor = dbb.rawQuery(sql, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        assignment.setId(Integer.parseInt(cursor.getString(0)));
        assignment.setStartDate(cursor.getString(1));
        assignment.setEndDate(cursor.getString(2));
        assignment.setMode(cursor.getString(3));
        assignment.setFlightDuration(cursor.getString(4));
        cursor.close();
        dbb.close();
        return assignment;
    }



    // *********************************************************************************************
}
