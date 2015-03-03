package com.example.crashnote.sqlitedb;

/* ownCloud Android client application
 *   Copyright (C) 2011  Bartek Przybylski
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.example.crashnote.CrashNote;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Custom database helper for Inclusion Media
 */
public class DBHandler {
	private static final int DATABASE_VERSION = 1; // Database Version
	private static final String DATABASE_NAME = "crashnote.db"; // Database Name
	private static final String TABLE_CRASHNOTE = "add_crashnote"; // Table Name

	// books table column names
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_DATE = "date";
	private static final String COLUMN_LONGITUDE = "longitude";
	private static final String COLUMN_LATITUDE = "latitude";
	private static final String COLUMN_NAME = "fname";
	private static final String COLUMN_ADDRESS = "address";
	private static final String COLUMN_CONTACT = "contact";
	private static final String COLUMN_REGISTRATION = "regno";
	private static final String COLUMN_CARMAKE = "carmake";
	private static final String COLUMN_CARMODEL = "carmodel";
	private static final String COLUMN_INSURANCE_COMPANY = "insurance_co";
	private static final String COLUMN_POLICE_INCNUMBER = "police_incnumber";
	private static final String COLUMN_ACCIDENT_INCNUMBER = "accident_incnumber";
	private static final String COLUMN_URI_ONE = "photo_one";
	private static final String COLUMN_URI_TWO = "photo_two";
	private static final String COLUMN_URI_THREE = "photo_three";
	private static final String COLUMN_URI_FOUR = "photo_four";

	// String to Create the Table
	private static final String CREATE_BOOKS_TABLE = "create table "
			+ TABLE_CRASHNOTE + " (" + COLUMN_ID
			+ " integer primary key autoincrement , " // 1
			+ COLUMN_DATE + " text not null," // 2
			+ COLUMN_LONGITUDE + " double, "// 3
			+ COLUMN_LATITUDE + " double, "// 4
			+ COLUMN_NAME + " text not null, "// 5
			+ COLUMN_ADDRESS + " integer, "// 6
			+ COLUMN_CONTACT + " bigint, "// 7
			+ COLUMN_REGISTRATION + " text not null, "// 7
			+ COLUMN_CARMAKE + " text not null, "// 8
			+ COLUMN_CARMODEL + " text not null, "// 9
			+ COLUMN_INSURANCE_COMPANY + " text not null, "// 10
			+ COLUMN_POLICE_INCNUMBER + " bigint, "// 11
			+ COLUMN_URI_ONE + " text, "// 12
			+ COLUMN_URI_TWO + " text, "// 13
			+ COLUMN_URI_THREE + " text, "// 14
			+ COLUMN_URI_FOUR + " text, "// 15
			+ COLUMN_ACCIDENT_INCNUMBER + " bigint);";// 16

	private SQLiteDatabase sqlitedb;
	private final Context ct;
	private MyDBHelper helper;

	public DBHandler(Context context) {
		this.ct = context;
		helper = new MyDBHelper(ct, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public DBHandler open() {
		sqlitedb = helper.getWritableDatabase();
		return this;
	}

	public DBHandler readDb() {
		sqlitedb = helper.getReadableDatabase();
		return this;

	}

	public void close() {
		sqlitedb.close();
	}

	// MyDBHelper is the class that extends SQLiteOpenHelper
	private static class MyDBHelper extends SQLiteOpenHelper {

		// this default constructor creates the SQLite Database
		public MyDBHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		// Overriding the onCreate method and creating Table
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_BOOKS_TABLE);
		}

		/**
		 * Overriding the onUpgrade method Dropping table if exists and calling
		 * onCreate which creates the table
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Updation", "Data Base Version is being Updated");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRASHNOTE);
			onCreate(db);
		}
	}

	/*
	 * Function to Insert values into Table add_crashnote
	 */
	public void insertEntry(String strDate, Double dblLongitude,
			Double dblLatitude, String strFName, int intAddress,
			long lngContact, String strRegNo, String strCarMake,
			String strCarModel, String strInsuranceCo, long lngPoliceIncNo,
			String uriOne, String uriTwo, String uriThree, String uriFour,
			long lngAccidentNo) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_DATE, strDate);
		contentValues.put(COLUMN_LONGITUDE, dblLongitude);
		contentValues.put(COLUMN_LATITUDE, dblLatitude);
		contentValues.put(COLUMN_NAME, strFName);
		contentValues.put(COLUMN_ADDRESS, intAddress);
		contentValues.put(COLUMN_CONTACT, lngContact);
		contentValues.put(COLUMN_REGISTRATION, strRegNo);
		contentValues.put(COLUMN_CARMAKE, strCarMake);
		contentValues.put(COLUMN_CARMODEL, strCarModel);
		contentValues.put(COLUMN_INSURANCE_COMPANY, strInsuranceCo);
		contentValues.put(COLUMN_POLICE_INCNUMBER, lngPoliceIncNo);
		contentValues.put(COLUMN_URI_ONE, uriOne);
		contentValues.put(COLUMN_URI_TWO, uriTwo);
		contentValues.put(COLUMN_URI_THREE, uriThree);
		contentValues.put(COLUMN_URI_FOUR, uriFour);
		contentValues.put(COLUMN_ACCIDENT_INCNUMBER, lngAccidentNo);

		sqlitedb.insert(TABLE_CRASHNOTE, null, contentValues);
		close();
	}

	/*
	 * Function to retrieve all the entries from the table add_crashnote
	 */
	public Cursor getAllEntries() {
		return sqlitedb.query(TABLE_CRASHNOTE, new String[] { COLUMN_ID,
				COLUMN_DATE, COLUMN_LONGITUDE, COLUMN_LATITUDE, COLUMN_NAME,
				COLUMN_ADDRESS, COLUMN_CONTACT, COLUMN_REGISTRATION,
				COLUMN_CARMAKE, COLUMN_CARMODEL, COLUMN_INSURANCE_COMPANY,
				COLUMN_POLICE_INCNUMBER, COLUMN_ACCIDENT_INCNUMBER,
				COLUMN_URI_ONE, COLUMN_URI_TWO, COLUMN_URI_THREE,
				COLUMN_URI_FOUR }, null, null, null, null, null, null);
	}

	public void addCrashNote(CrashNote crashnote) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_DATE, crashnote.getDate());
		contentValues.put(COLUMN_LONGITUDE, crashnote.getLongitude());
		contentValues.put(COLUMN_LATITUDE, crashnote.getLatitude());
		contentValues.put(COLUMN_NAME, crashnote.getfName());
		contentValues.put(COLUMN_ADDRESS, crashnote.getAddress());
		contentValues.put(COLUMN_CONTACT, crashnote.getContact());
		contentValues.put(COLUMN_REGISTRATION, crashnote.getRegNo());
		contentValues.put(COLUMN_CARMAKE, crashnote.getCarMake());
		contentValues.put(COLUMN_CARMODEL, crashnote.getCarModel());
		contentValues.put(COLUMN_INSURANCE_COMPANY, crashnote.getInsuranceCo());
		contentValues.put(COLUMN_POLICE_INCNUMBER, crashnote.getPoliceIncNo());
		contentValues.put(COLUMN_ACCIDENT_INCNUMBER, crashnote.getAccidentNo());
		contentValues.put(COLUMN_URI_ONE, crashnote.getPhotopath1());
		contentValues.put(COLUMN_URI_TWO, crashnote.getPhotopath2());
		contentValues.put(COLUMN_URI_THREE, crashnote.getPhotopath3());
		contentValues.put(COLUMN_URI_FOUR, crashnote.getPhotopath4());

		sqlitedb.insert(TABLE_CRASHNOTE, null, contentValues);
		close();
	}

	// To Update the value of the bookId passed as parameter
	public int UpdateEntry(int column_id, CrashNote crashnote) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_DATE, crashnote.getDate());
		contentValues.put(COLUMN_LONGITUDE, crashnote.getLongitude());
		contentValues.put(COLUMN_LATITUDE, crashnote.getLatitude());
		contentValues.put(COLUMN_NAME, crashnote.getfName());
		contentValues.put(COLUMN_ADDRESS, crashnote.getAddress());
		contentValues.put(COLUMN_CONTACT, crashnote.getContact());
		contentValues.put(COLUMN_REGISTRATION, crashnote.getRegNo());
		contentValues.put(COLUMN_CARMAKE, crashnote.getCarMake());
		contentValues.put(COLUMN_CARMODEL, crashnote.getCarModel());
		contentValues.put(COLUMN_INSURANCE_COMPANY, crashnote.getInsuranceCo());
		contentValues.put(COLUMN_POLICE_INCNUMBER, crashnote.getPoliceIncNo());
		contentValues.put(COLUMN_ACCIDENT_INCNUMBER, crashnote.getAccidentNo());
		contentValues.put(COLUMN_URI_ONE, crashnote.getPhotopath1());
		contentValues.put(COLUMN_URI_TWO, crashnote.getPhotopath2());
		contentValues.put(COLUMN_URI_THREE, crashnote.getPhotopath3());
		contentValues.put(COLUMN_URI_FOUR, crashnote.getPhotopath4());

		return sqlitedb.update(TABLE_CRASHNOTE, contentValues, COLUMN_ID + "="
				+ column_id, null);
	}

	// For Removing Values from table 'books'
	public void removeEntry(int column_id) {
		open();
		sqlitedb.delete(TABLE_CRASHNOTE, COLUMN_ID + "=" + column_id, null);
		close();
	}
	
	public void removeByDate(int id){
		open();
		sqlitedb.delete(TABLE_CRASHNOTE,COLUMN_ID + " = " + (id+1), null);
		close();
	}

	// Returns all the columns with a particular Something
	public Cursor getData(String strNewFName) {
		return sqlitedb.rawQuery(
				"Select * FROM add_crashnote WHERE fname='" + strNewFName
						+ "'", null);
	}

	// Getting Entry Count
	public int getEntryCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CRASHNOTE;
		readDb();
		Cursor cursor = sqlitedb.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
	 public List<String> getAllLabels(){
	    	List<String> labels = new ArrayList<String>();
	    	
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + TABLE_CRASHNOTE;
	     
	        readDb();
	        Cursor cursor = sqlitedb.rawQuery(selectQuery, null);
	     
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	            	labels.add(cursor.getString(1));
	            } while (cursor.moveToNext());
	        }
	        
	        // closing connection
	        cursor.close();
	        sqlitedb.close();
	    	
	    	// returning lables
	    	return labels;
	    }
	 public int deleteAll(){
		  return sqlitedb.delete(TABLE_CRASHNOTE, null, null);
		 }
		 
		 public void delete_byID(int id, CrashNote crashnote){
		  sqlitedb.delete(TABLE_CRASHNOTE, COLUMN_ID+"="+id, null);
		 }
		 
		 public void update_byID(int id,CrashNote crashnote){
			 open();
				ContentValues contentValues = new ContentValues();
				contentValues.put(COLUMN_DATE, crashnote.getDate());
				contentValues.put(COLUMN_LONGITUDE, crashnote.getLongitude());
				contentValues.put(COLUMN_LATITUDE, crashnote.getLatitude());
				contentValues.put(COLUMN_NAME, crashnote.getfName());
				contentValues.put(COLUMN_ADDRESS, crashnote.getAddress());
				contentValues.put(COLUMN_CONTACT, crashnote.getContact());
				contentValues.put(COLUMN_REGISTRATION, crashnote.getRegNo());
				contentValues.put(COLUMN_CARMAKE, crashnote.getCarMake());
				contentValues.put(COLUMN_CARMODEL, crashnote.getCarModel());
				contentValues.put(COLUMN_INSURANCE_COMPANY, crashnote.getInsuranceCo());
				contentValues.put(COLUMN_POLICE_INCNUMBER, crashnote.getPoliceIncNo());
				contentValues.put(COLUMN_ACCIDENT_INCNUMBER, crashnote.getAccidentNo());
				contentValues.put(COLUMN_URI_ONE, crashnote.getPhotopath1());
				contentValues.put(COLUMN_URI_TWO, crashnote.getPhotopath2());
				contentValues.put(COLUMN_URI_THREE, crashnote.getPhotopath3());
				contentValues.put(COLUMN_URI_FOUR, crashnote.getPhotopath4());
		  sqlitedb.update(TABLE_CRASHNOTE, contentValues, COLUMN_ID+"="+id, null);
		 }
}
