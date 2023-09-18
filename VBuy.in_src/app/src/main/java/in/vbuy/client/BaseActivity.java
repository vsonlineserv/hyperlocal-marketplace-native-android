package in.vbuy.client;




import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.nostra13.universalimageloader.core.ImageLoader;


public abstract class BaseActivity extends ActionBarActivity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private boolean instanceStateSaved;

	
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		instanceStateSaved = true;
	}

	@Override
	protected void onDestroy() {
		if (!instanceStateSaved) {
			imageLoader.stop();
		}
		super.onDestroy();
	}
}
