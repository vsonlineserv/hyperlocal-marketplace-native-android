package in.vbuy.client;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelpers extends SQLiteOpenHelper {
	static String DATABASE_NAME="userdata";
	public static final String TABLE_NAME="user";
	public static final String KEY_FNAME="address";
	public static final String KEY_LNAME="landmark";
	public static final String KEY_CONTACT="city";
	public static final String KEY_ADDRESS="state";
	public static final String KEY_MOBILE="mobile";
	public static final String KEY_PIN="pin";
	
	public static final String KEY_ID="id";
	public DbHelpers(Context context) {
		super(context, DATABASE_NAME, null, 8);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_FNAME+" TEXT, "+KEY_LNAME+" TEXT, "+KEY_CONTACT+" TEXT, "+KEY_ADDRESS+" TEXT, "+KEY_PIN+" TEXT, "+KEY_MOBILE+" TEXT)";
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);

	}

}
