package in.vbuy.client.util;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListHeight {
	 public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
             }
            listItem.measure(0, 0);
            Log.i("height of single:", String.valueOf(listItem.getMeasuredHeight()));
            totalHeight += listItem.getMeasuredHeight();
        }
      //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }
    public static void getListViewSize1(GridView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < (myListAdapter.getCount()/2); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
      //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = (totalHeight + (myListView.getHeight() * (myListAdapter.getCount() - 1)));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }
    public static void gridViewSetting(Activity a , GridView myListView ) {
		 ListAdapter myListAdapter = myListView.getAdapter();
	        if (myListAdapter == null) {
	            //do nothing return null
	            return;
	        }
	        //set listAdapter in loop for getting final size
	        int width = 0;
	        for (int size = 0; size < myListAdapter.getCount(); size++) {
	            View listItem = myListAdapter.getView(size, null, myListView);
	            if (listItem instanceof ViewGroup) {
	                listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	             }
	            listItem.measure(0, 0);
	            Log.i("width of single:", String.valueOf(listItem.getMeasuredWidth()));
	            width =( listItem.getMeasuredWidth());
	        }
	        int size=myListAdapter.getCount();
	        // Calculated single Item Layout Width for each grid element ....
	       
	        /*DisplayMetrics dm = new DisplayMetrics();
	        a.getWindowManager().getDefaultDisplay().getMetrics(dm);
	        float density = dm.density;
	        	Log.d("Density", ""+density);*/
	        int totalWidth = (int) (width * size * 1.5);
	        int singleItemWidth = (int) (width * 1.5);
	        

	       // int totalWidth = (int) ((width+50) * size );
	      //  int singleItemWidth = (int) (width +50);

	        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	                totalWidth, LinearLayout.LayoutParams.MATCH_PARENT);

	        myListView.setLayoutParams(params);
	        myListView.setColumnWidth(singleItemWidth);
	        myListView.setHorizontalSpacing(2);
	        myListView.setStretchMode(GridView.STRETCH_SPACING);
	        myListView.setNumColumns(size);
     
  }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
      if (listAdapter == null) {
      // pre-condition
            return;
      }

      int totalHeight = 0;
      for (int i = 0; i < listAdapter.getCount(); i++) {
           View listItem = listAdapter.getView(i, null, listView);
           if (listItem instanceof ViewGroup) {
              listItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
           }
           listItem.measure(0, 0);
           totalHeight += (listItem.getMeasuredHeight()+50);
      }
        int size=listAdapter.getCount();

      ViewGroup.LayoutParams params = listView.getLayoutParams();
      params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
                listView.setLayoutParams(params);
        listView.getCount();
        listView.setSelection(size);
                Log.i("height of listItem:", String.valueOf(totalHeight));
  }
    public static void setListViewHeightBasedOnChildren1(ListView myListView) {
    	 ListAdapter myListAdapter = myListView.getAdapter();
         if (myListAdapter == null) {
             //do nothing return null
             return;
         }
         //set listAdapter in loop for getting final size
         int totalHeight = 0;
         for (int size = 0; size < (myListAdapter.getCount()); size++) {
             View listItem = myListAdapter.getView(size, null, myListView);
             listItem.measure(0, 0);
             totalHeight += listItem.getMeasuredHeight()+50;
         }
       //setting listview item in adapter
         ViewGroup.LayoutParams params = myListView.getLayoutParams();
         params.height = totalHeight + (myListView.getHeight() * (myListAdapter.getCount() - 1));
         myListView.setLayoutParams(params);

         // print height of adapter on log
         Log.i("height of listItem:", String.valueOf(totalHeight));
     }
    
}

