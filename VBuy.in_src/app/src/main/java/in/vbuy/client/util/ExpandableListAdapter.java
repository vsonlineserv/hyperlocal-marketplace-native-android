package in.vbuy.client.util;

import in.vbuy.client.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Object> childtems;
	private LayoutInflater inflater;
	private ArrayList<String> parentItems, child;
	private Activity mContex;
	private LayoutInflater mInflater;
	ImageLoader imageLoader1;
	String[] childValues=null;
	String cat;
	public ExpandableListAdapter(Activity context, ArrayList<String> parents, ArrayList<Object> childern, String cat) {
		mContex = context;
		this.parentItems = parents;
		this.childtems = childern;
		this.activity=activity;
		this.cat=cat;
		mInflater = LayoutInflater.from(context);
		imageLoader1=new ImageLoader(context);
	}

	
	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		child = (ArrayList<String>) childtems.get(groupPosition);
		Log.d("Array", ""+child);
		childValues = child.toArray(new String[0]);
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.home_view, null);
		}
		ListView gridView = (ListView) convertView
				.findViewById(R.id.GridView_toolbar);

				GridAdapter adapter = new GridAdapter(mContex, childValues,cat);
		gridView.setAdapter(adapter);// Adapter
		ListAdapter myListAdapter1 = gridView.getAdapter();
		int size1=myListAdapter1.getCount();
		ListHeight.setListViewHeightBasedOnChildren(gridView);
		gridView.getCount();
		gridView.setSelection(size1);
		/*gridView.setAdapter(adapter);// Adapter
		ListAdapter myListAdapter1 = gridView.getAdapter();
		int size1=myListAdapter1.getCount();
		ListHeight.gridViewSetting(mContex,gridView);
		gridView.setNumColumns(size1);*/
		

		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.expandablelist_group, null);
		}
		

		((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
		((CheckedTextView) convertView).setChecked(isExpanded);

		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	 public class AddImgAdp extends BaseAdapter {
	        int GalItemBg;
	        private Context cont;
	        private Activity activity;  
	        private  LayoutInflater inflater=null; 
	        String[] childval;
	        public AddImgAdp(Context a,String[] child) {   
	        	cont = a;  
	        	childval=child;
	                 inflater = (LayoutInflater)cont.getSystemService
	                         (Context.LAYOUT_INFLATER_SERVICE);          } 
	        public int getCount() {
	            return childval.length;
	        }

	        public Object getItem(int position) {
	            return position;
	        }

	        public long getItemId(int position) {
	            return position;
	        }
	        public  class ViewHolder{   
	            public TextView text;    
	            public ImageView image;         } 
	        public View getView(int position, View convertView, ViewGroup parent) {
	        	
	        	View vi=convertView;   
	            ViewHolder holder;   
	            if(convertView==null){  
	                vi = inflater.inflate(R.layout.grid_item, null); 
	                holder=new ViewHolder();  
	                holder.text=(TextView)vi.findViewById(R.id.label);  
	              //  holder.image=(ImageView)vi.findViewById(R.id.img);   
	                vi.setTag(holder);             }  
	            else    

	                holder=(ViewHolder)vi.getTag();  
	                holder.text.setText(childval[position]); 
	               // imageLoader1.DisplayImage(childValues[position],0, holder.image);
	                return vi;     
	        	
	        	
	            
	            
	        }
	        
	    }
}