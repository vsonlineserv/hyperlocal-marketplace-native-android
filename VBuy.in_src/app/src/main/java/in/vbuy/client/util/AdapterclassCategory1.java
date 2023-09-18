package in.vbuy.client.util;

/**
 * Created by USERuser on 17-08-2016.
 */
import in.vbuy.client.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdapterclassCategory1 extends BaseAdapter {
    ViewHolder localViewHolder = null;
    private LayoutInflater mInflater;
    private Context context;
    //ArrayList<DEPT_HOLD1> dataObject;
    Object content = null;
    String []category=null;
    String []categoryid=null;
    Animation animation = null;
    public AdapterclassCategory1(Context context, String[] dataObject) {

        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.category=dataObject;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView != null) {
            localViewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = this.mInflater.inflate(R.layout.row_layout, null);


            //	View rowView = convertView;
            localViewHolder = new ViewHolder();



            localViewHolder.itemname = ((TextView) convertView
                    .findViewById(R.id.code));
            convertView.setTag(localViewHolder);


        }

        if (localViewHolder.itemname != null)

        {
            String temp =this.category[position];
            String[]splittemp= temp.split("~");
            localViewHolder.itemname.setText(splittemp[0]);
            //	Log.i("List name>>>>>>>>>>>>>>>>>",this.category[position] );
            localViewHolder.itemname.setTag(splittemp[1]);



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
