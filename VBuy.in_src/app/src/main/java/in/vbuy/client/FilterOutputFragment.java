package in.vbuy.client;
import in.vbuy.client.util.RangeSeekBar;
import in.vbuy.client.util.RangeSeekBar.OnRangeSeekBarChangeListener;
import in.vbuy.client.util.SubCategorySupportfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class FilterOutputFragment extends ListFragment {
    ListView output;
     ArrayAdapter<Filte> adapter1;
    	private Filte[] itemss;
 	 ArrayAdapter<Filte> listAdapter;
 	 ArrayAdapter<Filte> listAdapter1;
 	ArrayList<String> checked = new ArrayList<String>();
 	ArrayList<String> retrive = new ArrayList<String>();
 	ArrayList<String> data = new ArrayList<String>();
 	/*RangeSeekBar seek;*/
 	RelativeLayout rl;
 	TextView minimum,maximum; 
 	//RangeSeekBar<Integer> rangeSeekBar;
    public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup, Bundle savedInstanceState) {
    	
    	View view =inflater.inflate(R.layout.filter_output_fragment, viewGroup, false);
    	/*seek=(RangeSeekBar)view.findViewById(R.id.seekBar1);*/
    	rl=(RelativeLayout)view.findViewById(R.id.rl);
    	minimum=(TextView)view.findViewById(R.id.min);
    	maximum=(TextView)view.findViewById(R.id.max);
        return view;
        
       // return super.onCreateView(inflater, viewGroup, savedInstanceState);
    }
    
    
    public void price(int min, int max){
    	rl.setVisibility(View.INVISIBLE);
    	minimum.setVisibility(View.VISIBLE);
    	maximum.setVisibility(View.VISIBLE);
    	/*seek.setVisibility(View.VISIBLE);*/
        // Set the range
    	/*seek.setRangeValues(min, max);
    	if(SubCategorySupportfile.filterprice.size()>0){
    		seek.setSelectedMinValue(SubCategorySupportfile.filterprice.get("min"));
        	seek.setSelectedMaxValue(SubCategorySupportfile.filterprice.get("max"));
    	}
    	else{
    	seek.setSelectedMinValue(min);
    	seek.setSelectedMaxValue(max);
    	}
    	minimum.setText(""+seek.getSelectedMinValue());
  	    maximum.setText(""+seek.getSelectedMaxValue());
    	seek.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
    	        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
    	                // handle changed range values
    	        	//int a= rangeSeekBar.getSelectedMaxValue();
    	     	  // int b= rangeSeekBar.getSelectedMinValue();
    	     	   minimum.setText(""+minValue);
    	     	   maximum.setText(""+maxValue);
    	     	  SubCategorySupportfile.addprice(minValue, maxValue);
    	        }
    	});*/
    }
    // Clear Method
    
    public void display(final String[] outputarray ,final String filters){
    	rl.setVisibility(View.VISIBLE);
    	/*seek.setVisibility(View.INVISIBLE);*/
    	minimum.setVisibility(View.INVISIBLE);
    	maximum.setVisibility(View.INVISIBLE);
    	retrive.clear();
    	final ArrayList<Filte> planetList = new ArrayList<Filte>();
		for(int i=0;i<outputarray.length;i++){
		planetList.add(new Filte(outputarray[i]));
		}
		listAdapter = new SelectArralAdapter(getActivity(), planetList);
		ListView lv = getListView();
		for (int j = 0; j < SubCategorySupportfile.mylist.size(); j++) {
    		if(filters.equalsIgnoreCase(SubCategorySupportfile.mylist.get(j).get("FilterParameter")))
    		{
    			String listValue=SubCategorySupportfile.mylist.get(j).get("FilterValueText");
    			for(int f=0; f<planetList.size(); f++)
    			{
    				String name=planetList.get(f).getName();
    				if(name.equalsIgnoreCase(listValue))
    				{
    					
    				Filte planet = listAdapter.getItem(f);
    				planet.toggleChecked();
    				//View element = lv.getAdapter().getView(f, null, null);
    				//SelectViewHolder viewHolder = (SelectViewHolder) element
    				//		.getTag();
    				//viewHolder.getCheckBox().setChecked(planet.isChecked());
    					 }
    				else{}
    			}
    		}
    	}
    	
		setListAdapter(listAdapter);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
       	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
       			long arg3) {
       		Filte planet = listAdapter.getItem(position);
			planet.toggleChecked();
			SelectViewHolder viewHolder = (SelectViewHolder) arg1
					.getTag();
			viewHolder.getCheckBox().setChecked(planet.isChecked());
			if(planet.isChecked()){
				
				HashMap<String, String> map = new HashMap<String, String>();
		         map.put("FilterParameter", filters);
		         map.put("FilterValueText", planetList.get(position).getName());
		         SubCategorySupportfile. mylist.add(map);
			}
			else{
				for(int i=0; i<SubCategorySupportfile.mylist.size(); i++){
					if(SubCategorySupportfile.mylist.get(i).get("FilterValueText").equalsIgnoreCase(planetList.get(position).getName())){
						SubCategorySupportfile.mylist.remove(i);
					}
				}
				
			}
       		
       	}
       });
       }
       
    public void brandFilter(final String[] outputarray ,final String[] brandid,final String filters){
    	rl.setVisibility(View.VISIBLE);
    	/*seek.setVisibility(View.INVISIBLE);*/
    	minimum.setVisibility(View.INVISIBLE);
    	maximum.setVisibility(View.INVISIBLE);
    	retrive.clear();
    	//final ArrayList<Filte> planetList = new ArrayList<Filte>();
    	final ArrayList<Filte> planetList1 = new ArrayList<Filte>();
		for(int i=0;i<outputarray.length;i++){
		planetList1.add(new Filte(outputarray[i]));
		}
		listAdapter1 = new SelectArralAdapter(getActivity(), planetList1);
		ListView lv = getListView();
		if(SubCategorySupportfile.mybrand.size()>0){
		for (int j = 0; j < SubCategorySupportfile.mybrand.size(); j++) {
    		
    			String listValue=SubCategorySupportfile.mybrand.get(j).get("FilterValueText");
    			for(int f=0; f<planetList1.size(); f++)
    			{
    				String name=planetList1.get(f).getName();
    				if(name.equalsIgnoreCase(listValue))
    				{
    					
    				Filte planet = listAdapter1.getItem(f);
    				planet.toggleChecked();
    					 }
    				else{}
    			
    		}
    	}
		}
		setListAdapter(listAdapter1);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
       	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
       			long arg3) {
       		Filte planet = listAdapter1.getItem(position);
			planet.toggleChecked();
			SelectViewHolder viewHolder = (SelectViewHolder) arg1
					.getTag();
			viewHolder.getCheckBox().setChecked(planet.isChecked());
			if(planet.isChecked()){
				
				HashMap<String, String> map = new HashMap<String, String>();
		         map.put("FilterParameter", brandid[position]);
		         map.put("FilterValueText", planetList1.get(position).getName());
		         SubCategorySupportfile.mybrand.add(map);
			}
			else{
				for(int i=0; i<SubCategorySupportfile.mybrand.size(); i++){
					if(SubCategorySupportfile.mybrand.get(i).get("FilterValueText").equalsIgnoreCase(planetList1.get(position).getName())){
						SubCategorySupportfile.mybrand.remove(i);
					}
				}
				
			}
       		
       	}
       });
       }
       
		private static class SelectArralAdapter extends ArrayAdapter<Filte> {
		private LayoutInflater inflater;

		public SelectArralAdapter(Context context, List<Filte> planetList) {
			super(context, R.layout.filter_list_item, R.id.rowTextView, planetList);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// Planet to display
			Filte planet = (Filte) this.getItem(position);

			// The child views in each row.
			CheckBox checkBox;
			TextView textView;

			// Create a new row view
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.filter_list_item, null);

				// Find the child views.
				textView = (TextView) convertView
						.findViewById(R.id.rowTextView);
				checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);
				// Optimization: Tag the row with it's child views, so we don't
				// have to
				// call findViewById() later when we reuse the row.
				convertView.setTag(new SelectViewHolder(textView, checkBox));
				// If CheckBox is toggled, update the planet it is tagged with.
				/*checkBox.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						Filte planet = (Filte) cb.getTag();
						planet.setChecked(cb.isChecked());
					}
				});*/
			}
			// Reuse existing row view
			else {
				// Because we use a ViewHolder, we avoid having to call
				// findViewById().
				SelectViewHolder viewHolder = (SelectViewHolder) convertView
						.getTag();
				checkBox = viewHolder.getCheckBox();
				textView = viewHolder.getTextView();
			}

			// Tag the CheckBox with the Planet it is displaying, so that we can
			// access the planet in onClick() when the CheckBox is toggled.
			checkBox.setTag(planet);
			// Display planet data
			checkBox.setChecked(planet.isChecked());
			textView.setText(planet.getName());
			return convertView;
		}
	}

	
    }
    class SelectViewHolder {
		private CheckBox checkBox;
		private TextView textView;

		public SelectViewHolder() {
		}

		public SelectViewHolder(TextView textView, CheckBox checkBox) {
			this.checkBox = checkBox;
			this.textView = textView;
		}

		public CheckBox getCheckBox() {
			return checkBox;
		}

		public void setCheckBox(CheckBox checkBox) {
			this.checkBox = checkBox;
		}

		public TextView getTextView() {
			return textView;
		}

		public void setTextView(TextView textView) {
			this.textView = textView;
		}
	}
   