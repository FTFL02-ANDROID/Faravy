package com.faravy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class IcareSQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "iCare.db";
	private static final int DATABASE_VERSION = 1;

	// Common Column
	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	public static final String COL_DETAILS = "details";
	public static final String COL_DATE = "date";
	public static final String COL_TIME = "time";

	// Table Profile
	public static final String TABLE_PROFILE = "profile_info";
	public static final String COL_DOB = "dob";
	public static final String COL_HEIGHT = "height";
	public static final String COL_WEIGHT = "weight";

	// Table Doctor
	public static final String TABLE_DOCTOR = "doctor_info";
	public static final String COL_APPOINMENT = "appoinment";
	public static final String COL_PHONE = "phone";
	public static final String COL_EMAIL = "email";

	// Table Diet
	public static final String COL_DIET_ID = "diet_id";
	public static final String TABLE_DIET = "diet_info";
	public static final String COL_MANU = "manu";

	// Table Vaccine
	public static final String TABLE_VACCINE = "vaccine_info";

	// Table Health Center
	public static final String TABLE_HEALTH_CENTER = "health_center_info";
	public static final String COL_ADDRESS = "address";
	public static final String COL_LATTITUDE = "latitude";
	public static final String COL_LONGITUDE = "longitude";

	// Table Medical History
	public static final String TABLE_MEDICAL_HISTORY = "medical_history_info";
	public static final String COL_DOCTOR_NAME = "doctor_name";
	public static final String COL_PHOTO = "photo";

	// Profile table create statement
	private static final String CREATE_TABLE_PROFILE = "create table "
			+ TABLE_PROFILE + "( " + COL_ID
			+ " integer primary key autoincrement, " + " " + COL_NAME
			+ " text not null," + " " + COL_DOB + " text not null," + " "
			+ COL_HEIGHT + " text not null," + " " + COL_WEIGHT
			+ " text not null);";

	// Doctor table create statement
	private static final String CREATE_TABLE_DOCTOR = "create table "
			+ TABLE_DOCTOR + "( " + COL_ID
			+ " integer primary key autoincrement, " + " " + COL_NAME
			+ " text not null," + " " + COL_DETAILS + " text not null," + " "
			+ COL_APPOINMENT + " text not null," + " " + COL_PHONE
			+ " text not null," + " " + COL_EMAIL + " text not null);";

	// Diet table create statement
	private static final String CREATE_TABLE_DIET = "create table "
			+ TABLE_DIET + "( " + COL_DIET_ID
			+ " integer primary key autoincrement, " + " " + COL_NAME
			+ " text not null," + " " + COL_MANU + " text not null," + " "
			+ COL_DATE + " text not null," + " " + COL_TIME
			+ " text not null);";

	// Vaccine table create statement
	private static final String CREATE_TABLE_VACCINE = "create table "
			+ TABLE_VACCINE + "( " + COL_ID
			+ " integer primary key autoincrement, " + " " + COL_NAME
			+ " text not null," + " " + COL_DETAILS + " text not null," + " "
			+ COL_DATE + " text not null," + " " + COL_TIME
			+ " text not null);";

	// Health Center table create statement
	private static final String CREATE_TABLE_HEALTH_CENTER = "create table "
			+ TABLE_HEALTH_CENTER + "( " + COL_ID
			+ " integer primary key autoincrement, " + " " + COL_NAME
			+ " text not null," + " " + COL_ADDRESS + " text not null," + " "
			+ COL_LATTITUDE + " text not null," + " " + COL_LONGITUDE
			+ " text not null);";

	// Medical History table create statement
	private static final String CREATE_TABLE_MEDICAL_HISTORY = "create table "
			+ TABLE_MEDICAL_HISTORY + "( " + COL_ID
			+ " integer primary key autoincrement, " + " " + COL_DOCTOR_NAME
			+ " text not null," + " " + COL_DETAILS + " text not null," + " "
			+ COL_PHOTO + " BLOB  not null," + " "   + COL_DATE + " text not null);";

	// Create Database
	public IcareSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PROFILE);
		db.execSQL(CREATE_TABLE_DOCTOR);
		db.execSQL(CREATE_TABLE_DIET);
		db.execSQL(CREATE_TABLE_VACCINE);
		db.execSQL(CREATE_TABLE_HEALTH_CENTER);
		db.execSQL(CREATE_TABLE_MEDICAL_HISTORY);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(IcareSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH_CENTER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAL_HISTORY);
		onCreate(db);
	}

}
