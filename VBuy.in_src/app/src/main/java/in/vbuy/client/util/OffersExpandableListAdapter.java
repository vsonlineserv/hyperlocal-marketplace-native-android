package in.vbuy.client.util;

import in.vbuy.client.R;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class OffersExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Object> childtems;
	private LayoutInflater inflater;
	private ArrayList<String> parentItems, child;
	private Activity mContex;
	private LayoutInflater mInflater;

	public OffersExpandableListAdapter(Activity context, ArrayList<String> parents, ArrayList<Object> childern) {
		mContex = context;
		this.parentItems = parents;
		this.childtems = childern;
		this.activity=activity;
		mInflater = LayoutInflater.from(context);

	}

	
	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		child = (ArrayList<String>) childtems.get(groupPosition);
Log.d("Array", ""+child);
		TextView textView = null;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.view, null);
		}
		GridView gridView = (GridView) convertView
				.findViewById(R.id.GridView_toolbar);

		OffersGridAdapter adapter = new OffersGridAdapter(mContex, child);
		gridView.setAdapter(adapter);// Adapter
		ListAdapter myListAdapter1 = gridView.getAdapter();
		int size1=myListAdapter1.getCount();
		ListHeight.gridViewSetting(mContex,gridView);
		gridView.setNumColumns(size1);
		

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

}