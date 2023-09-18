package in.vbuy.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends Activity implements OnClickListener {
private Button btn_save;
private EditText edit_first,edit_last,edit_con,edit_pin,edit_mobile;
private DbHelpers mHelper;
private SQLiteDatabase dataBase;
private String id,fname,lname,con,addr,pin,mobile,spinner;
private boolean isUpdate;
Spinner state;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        
        btn_save=(Button)findViewById(R.id.button1);
        edit_first=(EditText)findViewById(R.id.editText1);
        edit_last=(EditText)findViewById(R.id.editText2);
        edit_con=(EditText)findViewById(R.id.editText3);
        edit_pin=(EditText)findViewById(R.id.editText5);
        edit_mobile=(EditText)findViewById(R.id.editText6);
        
        state=(Spinner)findViewById(R.id.state);
        
        
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.india, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter1);
        state.setOnItemSelectedListener(new MyOnItemSelectedListener());
         
       isUpdate=getIntent().getExtras().getBoolean("update");
        if(isUpdate)
        {
        	id=getIntent().getExtras().getString("ID");
        	fname=getIntent().getExtras().getString("Fname");
        	lname=getIntent().getExtras().getString("Lname");
        	con=getIntent().getExtras().getString("Con");
        	addr=getIntent().getExtras().getString("Addr");
        	pin=getIntent().getExtras().getString("Pin");
        	mobile=getIntent().getExtras().getString("mobile");
        	
        	
        	
        	
        	edit_first.setText(fname);
        	edit_last.setText(lname);
        	edit_con.setText(con);
        	edit_pin.setText(pin);
        	edit_mobile.setText(mobile);
        	state.setSelection(getIndex(state, addr));
        	
        	
        	
        }
         
         btn_save.setOnClickListener(this);
         
         mHelper=new DbHelpers(this);
        
    }
    private int getIndex(Spinner spinner, String myString)
    {
     int index = 0;

     for (int i=0;i<spinner.getCount();i++){
      if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
       index = i;
       break;
      }
     }
     return index;
    } 
    // saveButton click event 
	public void onClick(View v) {
		fname=edit_first.getText().toString().trim();
		lname=edit_last.getText().toString().trim();
		con=edit_con.getText().toString().trim();
		//addr=edit_addr.getText().toString().trim();
		pin=edit_pin.getText().toString().trim();
		mobile=edit_mobile.getText().toString().trim();
		
		spinner=state.getSelectedItem().toString();
		
		if(edit_first.getText().toString().equals(""))
        {
               //Toast.makeText(AddActivity.this, "Enter Your Full Name", Toast.LENGTH_SHORT).show();
			edit_first.setError("Enter Your Address ");
        }
		else if(edit_last.getText().toString().equals(""))
        {
               
			edit_last.setError("Enter Landamark");
        }
		else if(edit_con.getText().toString().equals(""))
        {
               
			edit_con.setError("Enter Your City");
        }
		
		else if(!isValidPin(pin))
		{
			edit_pin.setError("Invalid Pin");
		
		}
		
		else if(!isPhoneNumber(mobile))
		{
			edit_mobile.setError("Invalid Number");
		}
		else
		{
            saveData();
			
			Intent in=new Intent(AddActivity.this, DisplayActivity.class);

			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
		
		}
		
		
			
		

		
		
		
		
		/*saveData();
		
		Intent in=new Intent(AddActivity.this, DisplayActivity.class);

		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);*/

	}
	
	
		
	private boolean isPhoneNumber(String mobile)
	 {
		 String NUMBER = "^[7-9][0-9]{9}$";

			Pattern pattern = Pattern.compile(NUMBER);
			Matcher matcher = pattern.matcher(mobile);
			return matcher.matches();
	 }
	
	 private boolean isValidPin(String pin)
	 {
		 String PIN="^\\d{6}$";
				 
				 Pattern pattern = Pattern.compile(PIN);
			Matcher matcher = pattern.matcher(pin);
			return matcher.matches();
				 
	 }

	
	/* public boolean isCheck()
	 {
		 
			return false; 
	 }
	*/
	
	/**
	 * save data into SQLite
	 */
	private void saveData(){
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		values.put(DbHelpers.KEY_FNAME,fname);
		values.put(DbHelpers.KEY_LNAME,lname );
		values.put(DbHelpers.KEY_CONTACT,con);
		values.put(DbHelpers.KEY_ADDRESS,spinner );
		values.put(DbHelpers.KEY_PIN,pin);
		values.put(DbHelpers.KEY_MOBILE,mobile);
		
		
		System.out.println("");
		if(isUpdate)
		{    
			//update database with new data 
			dataBase.update(DbHelpers.TABLE_NAME, values, DbHelpers.KEY_ID+"="+id, null);
		}
		else
		{
			//insert data into database
			dataBase.insert(DbHelpers.TABLE_NAME, null, values);
		}
		//close database
		dataBase.close();
		
		
		
	}

}
