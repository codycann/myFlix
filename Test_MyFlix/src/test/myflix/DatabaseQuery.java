package test.myflix;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class DatabaseQuery {
	// Variables area
	private ArrayList<String> arrayKeys = null;
	private ArrayList<String> arrayValues = null;
	private ArrayList<String> databaseKeys = null;
	private ArrayList<String> databaseKeyOptions = null;
	private DBAdapter database;
	
	public DatabaseQuery(Context context) {
		databaseKeys = new ArrayList<String>();
		databaseKeyOptions = new ArrayList<String>();
		databaseKeys.add("filename");
		databaseKeys.add("title");
		databaseKeys.add("year");
		databaseKeys.add("rated");
		databaseKeys.add("runtime");
		databaseKeys.add("awards");
		databaseKeys.add("plot");
		databaseKeys.add("imdbRating");
		databaseKeys.add("genre");
		databaseKeys.add("director");
		databaseKeys.add("writer");
		databaseKeys.add("actors");
		databaseKeyOptions.add("text not null");
		
		database = new DBAdapter(context, "SiteData", databaseKeys, databaseKeyOptions);
        database.open();
		arrayKeys = new ArrayList<String>();
		arrayValues = new ArrayList<String>();

	}
	
	public void appendData(String key, String value){
		arrayKeys.add(key);
		arrayValues.add(value);
	}
	
	public void addRow(){
		database.insertEntry(arrayKeys, arrayValues);
	}
	
	public void deleteAll(){
		database.clearTable();
	}
	
	/**
	 * Get data from the table.
	 * @param keys List of columns to include in the result.
	 * @param selection Return rows with the following string only. Null returns all rows.
	 * @param selectionArgs Arguments of the selection.
	 * @param groupBy Group results by.
	 * @param having A filter declare which row groups to include in the cursor.
	 * @param sortBy Column to sort elements by.
	 * @param sortOption ASC for ascending, DESC for descending.
	 * @return Returns an ArrayList<String> with the results of the selected field.
	 */
	public ArrayList<String> getCol(String key, String selection, String[] 
		    selectionArgs, String groupBy, String having, String sortBy, String sortOption){
			String[] column = {key};
			ArrayList<String> list = new ArrayList<String>(); 
			Cursor results = database.getAllEntries(column, selection, 
					selectionArgs, groupBy, having, sortBy, sortOption);
			while(results.moveToNext())
				list.add(results.getString(0));
			results.close();
			return list;

	}
	private ArrayList<String> getRow(String selection, String[] 
		  selectionArgs){
			ArrayList<String> list = new ArrayList<String>(); 
			Cursor results = database.getAllEntries(null, selection, 
					selectionArgs, null, null, null, "");
			while(results.moveToNext())
				for(int i = 0; i < databaseKeys.size(); i++){
					list.add(results.getString(results.getColumnIndex(databaseKeys.get(i))));
				}
			results.close();
			return list;
	}
	public ArrayList<ArrayList<String>> getAll(String[] keys, String selection, String[] 
		  selectionArgs, String groupBy, String having, String sortBy, String sortOption){
			ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>(); 
			Cursor results = database.getAllEntries(keys, selection, 
					selectionArgs, groupBy, having, sortBy, sortOption);
			while(results.moveToNext()){
				ArrayList<String> row = new ArrayList<String>();
				for(int i = 0; i < keys.length; i++){
					row.add(results.getString(results.getColumnIndex(keys[i])));
				}
				list.add(row);
			}
			results.close();
			return list;
	}
	public ArrayList<String> byTitle(String title){
		return getRow("title = ?", new String[]{title});
	}
	public ArrayList<String> titleByGenre(String genre){
		return getCol("title", "title like ", new String[]{"%"+genre+"%"}, null, null, null, "");
	}
	
	public void destroy() throws Throwable{
        database.close();
	}
}
