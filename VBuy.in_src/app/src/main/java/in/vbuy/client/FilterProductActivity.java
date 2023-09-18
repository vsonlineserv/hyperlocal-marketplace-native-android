package in.vbuy.client;
import in.vbuy.client.util.SubCategorySupportfile;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
public class FilterProductActivity extends FragmentActivity implements OnClickListener{
    LinearLayout clear,apply;
    final Context context = this;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_main);
        clear=(LinearLayout)findViewById(R.id.clear_filter);
        apply=(LinearLayout)findViewById(R.id.apply_filter);
        clear.setOnClickListener(this);
        apply.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.clear_filter) {
			
			SubCategorySupportfile.mylist.clear();
			SubCategorySupportfile.mybrand.clear();
			SubCategorySupportfile.filterprice.clear();
			FilterFragment txt = (FilterFragment)getFragmentManager().findFragmentById(R.id.menu);
			txt.position(SubCategorySupportfile.position.get("position"));
		}
		if (v.getId() == R.id.apply_filter) {
			Intent intent = new Intent(
					FilterProductActivity.this.getApplicationContext(),
					ProductlistActivity.class);
			intent.putExtra("productid", SubCategorySupportfile.filterid.get("productid"));
			intent.putExtra("productname", SubCategorySupportfile.filterid.get("productname"));
			intent.putExtra("mapradius", "5");
			intent.putExtra("productname", SubCategorySupportfile.filterid.get("search"));
			if(SubCategorySupportfile.filterprice.size()>0){
			intent.putExtra("from",  SubCategorySupportfile.filterprice.get("min").toString());
			intent.putExtra("to", SubCategorySupportfile.filterprice.get("max").toString());
			}
			else{
				intent.putExtra("from","0");
				intent.putExtra("to","100000");
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			SubCategorySupportfile.position.clear();
	        finish();
			startActivity(intent);
		}
	}
	public void onBackPressed(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
 
			// set title
			alertDialogBuilder.setTitle("Exit Filter");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Your Filter All Selections Clear")
				.setCancelable(false)
				.setNegativeButton("CLEAR & EXIT",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						SubCategorySupportfile.mylist.clear();
						SubCategorySupportfile.mybrand.clear();
						SubCategorySupportfile.filterprice.clear();
						SubCategorySupportfile.position.clear();
						 finish();
						dialog.cancel();
						}
				  })
				.setPositiveButton("APPLY & EXIT",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						Intent intent = new Intent(
								FilterProductActivity.this.getApplicationContext(),
								ProductlistActivity.class);
						intent.putExtra("productid", SubCategorySupportfile.filterid.get("productid"));
						intent.putExtra("productname", SubCategorySupportfile.filterid.get("productname"));
						intent.putExtra("mapradius", "5");
						intent.putExtra("productname", SubCategorySupportfile.filterid.get("search"));
						if(SubCategorySupportfile.filterprice.size()>0){
							intent.putExtra("from",  SubCategorySupportfile.filterprice.get("min").toString());
							intent.putExtra("to", SubCategorySupportfile.filterprice.get("max").toString());
							}
							else{
								intent.putExtra("from","0");
								intent.putExtra("to","100000");
							}
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						SubCategorySupportfile.position.clear();
				        finish();
						startActivity(intent);
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();

	}
    
}
