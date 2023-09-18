package in.vbuy.client.util;


import in.vbuy.client.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FilterMainAdapter extends BaseAdapter {
	ViewHolder localViewHolder = null;
	private LayoutInflater mInflater;
	private Context context;
	//ArrayList<DEPT_HOLD1> dataObject;
	Object content = null;
	String []category=null;
	String []categoryid=null;
	Animation animation = null;
	public FilterMainAdapter(Context context, String[] dataObject) {
		
		this.context = context;
		this.mInflater = LayoutInflater.from(this.context);
		this.category=dataObject;
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView != null) {
			localViewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = this.mInflater.inflate(R.layout.filter_main_row, null);
		
			
	//	View rowView = convertView;
			localViewHolder = new ViewHolder();
			
			
			
			localViewHolder.itemname = ((TextView) convertView
					.findViewById(R.id.filter_main));
			convertView.setTag(localViewHolder);
			
		}
		
		if (localViewHolder.itemname != null) 
			
		{
			String temp =this.category[position];
			localViewHolder.itemname.setText(temp);
		}
		
		
	
		return convertView;
	}

	public int getCount() {
		int i = 0;
		try {
			i = this.category.length
					;
		} catch (Exception localException) {
		}
		return i;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	static class ViewHolder {
		public TextView itemname;
	}

	
	
	
}
