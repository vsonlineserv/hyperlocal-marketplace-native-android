package in.vbuy.client;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBLogin {
  public static final String KEY_ROWID = "_id";
  public static final String KEY_AREANAME = "areaname";
  public static final String KEY_LATITUDE = "latitude";
  public static final String KEY_LONGITUDE = "longitude";
  private static final String TAG = "DBAdapter";

  private static final String DATABASE_NAME = "MyDB1";
  private static final String DATABASE_TABLE = "contacts";
  private static final int DATABASE_VERSION = 2;

  private static final String DATABASE_CREATE = "create table contacts (_id integer primary key autoincrement, "
      + "areaname text not null, latitude text not null, longitude text not null);";

  private final Context context;

  private DatabaseHelper1 DBHelper;
  private SQLiteDatabase db;

  public DBLogin(Context ctx) {
    this.context = ctx;
    DBHelper = new DatabaseHelper1(context);
  }

  private static class DatabaseHelper1 extends SQLiteOpenHelper {
    DatabaseHelper1(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      try {
        db.execSQL(DATABASE_CREATE);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.w(TAG, oldVersion + " to " + newVersion
          + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS contacts");
      onCreate(db);
    }
  }

  public DBLogin open() throws SQLException {
    db = DBHelper.getWritableDatabase();
    return this;
  }

  public void close() {
    DBHelper.close();
  }

  public long insertContact(String areaname, String latitude ,String longitude) {
    ContentValues initialValues = new ContentValues();
    initialValues.put(KEY_AREANAME, areaname);
    initialValues.put(KEY_LATITUDE, latitude);
    initialValues.put(KEY_LONGITUDE, longitude);
    return db.insert(DATABASE_TABLE, null, initialValues);
  }

  public boolean Delete(int id) {
	  db.delete(DATABASE_TABLE, KEY_ROWID +  "=" + id, null) ;
	  context.deleteDatabase(DATABASE_NAME);
	    return true;
	    
	}

  
  public boolean getAllContactscount(long rowId) {
	  Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
		        KEY_ROWID, KEY_AREANAME, KEY_LATITUDE,KEY_LONGITUDE }, KEY_ROWID + "=" + rowId,
		        null, null, null, null, null,null);
		    if (mCursor != null) {
		      mCursor.moveToFirst();
		    }
		   
		    return  mCursor.getCount()>0;
	  }

  public Cursor getAllContacts() {
    return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_AREANAME,
        KEY_LATITUDE,KEY_LONGITUDE }, null, null, null, null, null,null);
  }

  public Cursor getContact(long rowId) throws SQLException {
    Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
        KEY_ROWID, KEY_AREANAME, KEY_LATITUDE,KEY_LONGITUDE }, KEY_ROWID + "=" + rowId,
        null, null, null, null, null,null);
    if (mCursor != null) {
      mCursor.moveToFirst();
    }
    return mCursor;
  }
  
 

  public boolean updateContact(long rowId, String areaname, String latitude, String longitude) {
    ContentValues args = new ContentValues();
    args.put(KEY_AREANAME, areaname);
    args.put(KEY_LATITUDE, latitude);
    args.put(KEY_LONGITUDE, longitude);
    return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
  }
}