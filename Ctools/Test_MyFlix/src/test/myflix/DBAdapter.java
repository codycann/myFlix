package test.myflix;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.*;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBAdapter{
	private static final String DATABASE_NAME = "movies.db";
	private String DATABASE_TABLE;
	private static final int DATABASE_VERSION = 1;
	public static final String KEY_ID = "_id";
	
	public  ArrayList<String> TABLE_KEYS =  new ArrayList<String>();
	public  ArrayList<String> TABLE_OPTIONS = new ArrayList<String>();
	public  final String KEY_TIMESTAMP = "timeStamp";
	public  final int TIMESTAMP_COLUMN = 1;
	
	private String DATABASE_CREATE;
	private SQLiteDatabase db;
	private myDBHelper dbHelper;
	
	@SuppressWarnings("unchecked")
	public DBAdapter(Context context, String table, ArrayList<String> keys, ArrayList<String> options){
		DATABASE_TABLE = table;
		TABLE_KEYS = (ArrayList<String>)keys.clone();
		TABLE_OPTIONS = options;
		
		String keyString = "";
		for(int i = 0; TABLE_KEYS.size() > i; i++){
			
			if(i + 1 < TABLE_OPTIONS.size() && TABLE_OPTIONS.get(i) != null){
				TABLE_OPTIONS.set(i, TABLE_OPTIONS.get(i) + ",");
			}else if (i + 1 == TABLE_OPTIONS.size() && TABLE_OPTIONS.get(i) != null) {
				if(i + 1 < TABLE_KEYS.size()){
					TABLE_OPTIONS.set(i, TABLE_OPTIONS.get(i) + ",");
				}else {
					TABLE_KEYS.set(i, TABLE_KEYS.get(i) + "");
				}
			}else if (i + 1 != TABLE_KEYS.size()) {
				TABLE_KEYS.set(i, TABLE_KEYS.get(i) + ",");
			}else {
				TABLE_KEYS.set(i, TABLE_KEYS.get(i) + "");
			}
			
			System.out.println(TABLE_OPTIONS.toString());
			System.out.println(TABLE_KEYS.toString());
			
			if(i + 1 <= TABLE_OPTIONS.size() && TABLE_OPTIONS.get(i) != null)
				keyString = keyString + " " + TABLE_KEYS.get(i) + " " + TABLE_OPTIONS.get(i);
			else if(i + 1 > TABLE_OPTIONS.size() || TABLE_OPTIONS.get(i) == null){
				keyString = keyString + " " + TABLE_KEYS.get(i);
			}
		}
		
		DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " ("
		+ "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TIMESTAMP + "," + keyString + ");";
		
		Log.v("Database Creation String", DATABASE_CREATE);
		dbHelper = new myDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION,
				DATABASE_TABLE, DATABASE_CREATE);
	}
	
	public DBAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}
	
	public long insertEntry(ArrayList<String> key, ArrayList<String> value) {
		String timeStamp = new Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
		ContentValues contentValues = new ContentValues();
		for(int i = 0; key.size() > i; i++){
			contentValues.put(key.get(i), value.get(i));
		}
		contentValues.put(KEY_TIMESTAMP, timeStamp);
		Log.v("Database Add", contentValues.toString());
		return db.insert(DATABASE_TABLE, null, contentValues);
	}
	
	public boolean removeEntry(long rowIndex) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowIndex, null) > 0;
	}
	
	/**
	 * Get all entries in the database sorted by the given value.
	 * @param columns List of columns to include in the result.
	 * @param selection Return rows with the following string only. Null returns all rows.
	 * @param selectionArgs Arguments of the selection.
	 * @param groupBy Group results by.
	 * @param having A filter declare which row groups to include in the cursor.
	 * @param sortBy Column to sort elements by.
	 * @param sortOption ASC for ascending, DESC for descending.
	 * @return Returns a cursor through the results.
	 */
	public Cursor getAllEntries(String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String sortBy, String sortOption) {
		return db.query(DATABASE_TABLE, columns, selection, selectionArgs, groupBy,
				having, sortBy + sortOption);
	}
	
	/** 
	 * Same as above with the optional limit
	 * @param limit limiting number of records to return
	 * @return Returns a cursor through the results.
	 */
	public Cursor getAllEntries(String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String sortBy, String sortOption, String limit) {
		return db.query(DATABASE_TABLE, columns, selection, selectionArgs, groupBy,
				having, sortBy + sortOption, limit);
	}

	public void update(String sqlQuery) {
		db.rawQuery("UPDATE " + DATABASE_TABLE + sqlQuery, null);		
	}
	
	public boolean clearTable() {
		return db.delete(DATABASE_TABLE, null, null) > 0;	
	}
	
	/**
	 * Update the selected row of the open table.
	 * @param rowIndex Number of the row to update.
	 * @param key ArrayList of Keys (column headers).
	 * @param value ArrayList of Key values.
	 * @return Returns an integer.
	 */
	public int updateEntry(long rowIndex, ArrayList<String> key, ArrayList<String> value) {
		String timeStamp = new Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
		String where = KEY_ID + "=" + rowIndex;
		ContentValues contentValues = new ContentValues();
		for(int i = 0; key.size() > i; i++){
			contentValues.put(key.get(i), value.get(i));
		}
		contentValues.put(KEY_TIMESTAMP, timeStamp);
		return db.update(DATABASE_TABLE, contentValues, where, null);
	}
	
	private static class myDBHelper extends SQLiteOpenHelper {
		private String creationString;
		private String tableName;
		@SuppressWarnings("unused")
		SQLiteDatabase db;
		
		public myDBHelper(Context context, String name, CursorFactory factory,
				int version, String tableName, String creationString) {			
			super(context, name, factory, version);
			this.creationString = creationString;
			this.tableName = tableName;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(creationString);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Log the version upgrade
			Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion +
					" to " + newVersion + ", which will destroy all old data");
			
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
			onCreate(db);
			
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			db.execSQL(creationString);
		}
		
	}	
}
