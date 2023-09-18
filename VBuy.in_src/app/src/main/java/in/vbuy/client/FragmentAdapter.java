package in.vbuy.client;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentAdapter extends BaseAdapter {

	ViewHolder localViewHolder = null;
	private LayoutInflater mInflater;
	private Context context;
	String log_button;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	//ArrayList<DEPT_HOLD1> dataObject;
	Object content = null;
	String []category=null;
	String []categoryid=null;
	Animation animation = null;
	public FragmentAdapter(Context context, String[] dataObject) {
		
		this.context = context;
		this.mInflater = LayoutInflater.from(this.context);
		this.category=dataObject;
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.hidescreen)
		.cacheInMemory()
		.cacheOnDisc()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView != null) {
			localViewHolder = (ViewHolder) convertView.getTag();
		} else {
			convertView = this.mInflater.inflate(R.layout.fragment, null);
		
			
	//	View rowView = convertView;
			localViewHolder = new ViewHolder();
			
			
			
			localViewHolder.itemname = ((TextView) convertView
					.findViewById(R.id.price));
			convertView.setTag(localViewHolder);
			localViewHolder.MenuImages=((ImageView) convertView
					.findViewById(R.id.img));
			
		}
		
		if (localViewHolder.itemname != null) 
			
		{
			String temp =this.category[position];
			animation = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
			String[]splittemp= temp.split("~");
			localViewHolder.itemname.setText(splittemp[0]);
		//	Log.i("List name>>>>>>>>>>>>>>>>>",this.category[position] );
			localViewHolder.itemname.setTag(splittemp[1]);
			String link="http://images.vbuy.in/VBuyImages/CategoryIcons/";
			String img=link+"i"+(splittemp[0].replaceAll("\\s", "").replaceAll("&", ""))+".png";
			imageLoader.displayImage(img,localViewHolder.MenuImages, options, new SimpleImageLoadingListener() {
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
					

					localViewHolder.MenuImages.setImageResource(R.drawable.hidescreen);
				}
				});
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
	
		public ImageView MenuImages;
		
		public ProgressBar progressbar;
		RelativeLayout mealslayout;
	}

	

}
