package in.vbuy.client.util;

import in.vbuy.client.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class DescriptionAdapter extends ArrayAdapter<Item> {
 
 private Context context;
 private ArrayList<Item> items;
 private LayoutInflater vi;
 
 public DescriptionAdapter(Context context,ArrayList<Item> items) {
  super(context,0, items);
  this.context = context;
  this.items = items;
  vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 }
 
 
 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
  View v = convertView;
 
  final Item i = items.get(position);
  if (i != null) {
   if(i.isSection()){
    SectionItem si = (SectionItem)i;
    v = vi.inflate(R.layout.list_item_section, null);
 
    v.setOnClickListener(null);
    v.setOnLongClickListener(null);
    v.setLongClickable(false);
     
    final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
    sectionView.setText(si.getTitle());
    sectionView.setTextColor(this.context.getResources()
			.getColorStateList(R.color.green));
   }
   else{
   
    EntryItem ei = (EntryItem)i;
    v = vi.inflate(R.layout.description_entry, null);
    v.setOnClickListener(null);
    v.setOnLongClickListener(null);
    v.setLongClickable(false);
    final TextView title = (TextView)v.findViewById(R.id.list_item_entry_title);
    final TextView spec = (TextView)v.findViewById(R.id.specification);
    
    if (title != null) 
     title.setText(ei.title);
     title.setTextColor(this.context.getResources()
			.getColorStateList(R.color.black));
     if (spec != null) 
    	 spec.setText(ei.subtitle);
     spec.setTextColor(this.context.getResources()
    			.getColorStateList(R.color.black));
    
   }
  }
  return v;
 }
 
}
 

