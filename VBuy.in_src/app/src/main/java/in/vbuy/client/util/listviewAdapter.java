package in.vbuy.client.util;

import in.vbuy.client.R;
import in.vbuy.client.galleryView.AddImgAdp.ViewHolder;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class listviewAdapter extends  ArrayAdapter<Productpojo> {
	
	ArrayList<Productpojo> productlist;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;
	Context cnt;
	DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
public listviewAdapter(Context context, int resource, ArrayList<Productpojo> objects) {
	super(context, resource, objects);
	vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	Resource = resource;
	productlist = objects;
	cnt=context;
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.hidescreen)
		.cacheInMemory()
		.cacheOnDisc()
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();

	
		
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		
		final View rowView = vi.inflate(Resource, null);
		
		final ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);
		final ProgressBar spinner = (ProgressBar) rowView.findViewById(R.id.loadingBar);
		final TextView name=(TextView)rowView.findViewById(R.id.name);
		final TextView sprice=(TextView)rowView.findViewById(R.id.code);
		final TextView stores=(TextView)rowView.findViewById(R.id.store);
		final TextView price=(TextView)rowView.findViewById(R.id.price);

		String checkname=(productlist.get(position).getProname());
		if(checkname.length()>45){
		String result = checkname.substring(0, 45) + "...";
		name.setText(result);
		}
		else{
		name.setText(checkname);
		}
		sprice.setText(""+(int) (Double.parseDouble(productlist.get(position).getDescription()))+".00");
		stores.setText(productlist.get(position).getStoreslist()+" Stores");
		name.setTextColor(this.cnt.getResources()
				.getColorStateList(R.color.green));
		price.setText(price.getText().toString()+((int) (Double.parseDouble(productlist.get(position).getId())))+".00");
		price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		
		imageLoader.displayImage(productlist.get(position).getImgurl(), imageView, options, new SimpleImageLoadingListener() {
			public void onLoadingStarted() {
				spinner.setVisibility(View.VISIBLE);
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
				

				spinner.setVisibility(View.GONE);
				imageView.setImageResource(R.drawable.hidescreen);
			}

			public void onLoadingComplete(Bitmap loadedImage) {
				spinner.setVisibility(View.GONE);
			}
		});

	
		return rowView;
	}
	
}