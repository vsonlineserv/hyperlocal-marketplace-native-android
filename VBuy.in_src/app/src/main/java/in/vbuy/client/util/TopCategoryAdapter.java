package in.vbuy.client.util;

import in.vbuy.client.ProductlistActivity;
import in.vbuy.client.R;
import in.vbuy.client.SellerListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class TopCategoryAdapter extends BaseAdapter {

	private Context mContext;
	private String[] childValues;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	
	public TopCategoryAdapter(Context context,  String[]  childlist) {
		mContext = context;
		childValues = childlist;
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.hidescreen)
		.cacheInMemory()
		.cacheOnDisc()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}

	@Override
	public int getCount() {
		return childValues.length;
	}

	@Override
	public String getItem(int position) {
		return childValues[position];
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		final View rowView = inflater.inflate(R.layout.top_categoryrow, parent, false);
		
			final TextView name = (TextView) rowView.findViewById(R.id.name);
			final ImageView product_img = (ImageView) rowView.findViewById(R.id.offer_img);
			final ImageView hide = (ImageView) rowView.findViewById(R.id.hide_img);

			
		final String[]splittemp= childValues[position].split("~");
		name.setText(splittemp[1]);
		final String id=(splittemp[0]);
	    final String img_url="http://images.vbuy.in/VBuyImages/TopCategories/"+splittemp[1].replaceAll("[\\p{Punct}\\s\\d]+", " ").replaceAll("\\s", "")+".jpg";
		imageLoader.displayImage(img_url,product_img, options, new SimpleImageLoadingListener() {
			public void onLoadingStarted() {
				//spinner.setVisibility(View.VISIBLE);
			}

			public void onLoadingFailed(FailReason failReason) {
				String message = null;
				switch (failReason) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case OUT_OF_MEMORY:
						message = "Out Of Memory error";
						break;
					case UNKNOWN:
						message = "Unknown error";
						break;
				}
				

				//spinner.setVisibility(View.GONE);
				product_img.setImageResource(R.drawable.hidescreen);
			}

			public void onLoadingComplete(Bitmap loadedImage) {
				hide.setVisibility(View.INVISIBLE);
			}
		});
		
		
		
		rowView.setOnClickListener(new OnClickListener() {
			 
			             public void onClick(View view) {
			
			            	 Intent int1 = new Intent(mContext, ProductlistActivity.class);

			            	 int1.putExtra("productname",splittemp[1] );
			            	 int1.putExtra("productid", splittemp[0]);
			            	 int1.putExtra("mapradius", "5");
			            	 int1.putExtra("from", "0");
			            	 int1.putExtra("to", "100000");
			            	 int1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			            	 mContext.startActivity(int1);
			
			             }
			
			         });

		return rowView;
		
	}
	
	static class ViewHolder {
		TextView name;
		TextView offer;
		TextView from;
		TextView to;
		TextView stores;
		ImageView product_img;
	}
	
}